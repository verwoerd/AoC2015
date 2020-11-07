package part1

import java.io.BufferedReader
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.streams.asSequence

/**
 * @author verwoerd
 * @since 06-11-20
 */
@ExperimentalUnsignedTypes
fun part1(input: BufferedReader): String {
  input.lines().asSequence().forEach(::parseLine)
  process()
  // note: since i got compiler errors i only cast to unsigned short in the end rather then the whole process
  return values.toSortedMap().map { "${it.key}: ${it.value.toUShort()}" }.joinToString("\n")
}

val valueRegex = Regex("^(\\d+)$")
val copyRegex = Regex("^(\\w+)$")
val andRegex = Regex("^(\\w+) AND (\\w+)$")
val orRegex = Regex("^(\\w+) OR (\\w+)$")
val leftShiftRegex = Regex("^(\\w+) LSHIFT (\\w+)$")
val rightShiftRegex = Regex("^(\\w+) RSHIFT (\\w+)$")
val notRegex = Regex("^NOT (\\w+)$")
val dependencies = mutableMapOf<String, MutableSet<String>>().withDefault { mutableSetOf() }
val resolved = mutableSetOf<String>()
val targets = mutableMapOf<String, Signal>()
val values = mutableMapOf<String, Short>()
var count = 0

fun process() {
  while(resolved.size != count) {
    resolved.mapNotNull { dependency -> dependencies[dependency] }
      .forEach(::processOthers)
  }
}

fun parseLine(line: String) {
  val (operation, target) = line.split(" -> ")
  count++
  val signal = when {
    notRegex.matches(operation) -> Signal(target, Operation.NOT, notRegex.matchEntire(operation)!!.groupValues[1])
    andRegex.matches(operation) -> {
      val (_, a, b) = andRegex.matchEntire(operation)!!.groupValues
      Signal(target, Operation.AND, a, b)
    }
    orRegex.matches(operation) -> {
      val (_, a, b) = orRegex.matchEntire(operation)!!.groupValues
      Signal(target, Operation.OR, a, b)
    }
    leftShiftRegex.matches(operation) -> {
      val (_, a, b) = leftShiftRegex.matchEntire(operation)!!.groupValues
      Signal(target, Operation.LSHIFT, a, b)
    }
    rightShiftRegex.matches(operation) -> {
      val (_, a, b) = rightShiftRegex.matchEntire(operation)!!.groupValues
      Signal(target, Operation.RSHIFT, a, b)
    }
    valueRegex.matches(operation) -> Signal(
      target,
      Operation.ASSIGN,
      valueRegex.matchEntire(operation)!!.groupValues[1]
                                           )
    copyRegex.matches(operation) -> Signal(target, Operation.ASSIGN, copyRegex.matchEntire(operation)!!.groupValues[1])
    else -> error("Can't parse line $line")
  }
  targets[target] = signal

  // verify resolved
  if (signal.isResolved()) {
    signal.resolve()
  }

  if (signal.left.isConstant()) {
    if(resolved.add(signal.left))
      count++

  } else {
    dependencies[signal.left] = dependencies.getValue(signal.left).also { it.add(signal.target) }
  }
  if (signal.right.isNotEmpty()) {
    if (signal.right.isConstant()) {
      if(resolved.add(signal.right))count++
    } else {
      dependencies[signal.right] = dependencies.getValue(signal.right).also { it.add(signal.target) }
    }
  }
}

fun processOthers(toResolve: Collection<String>) {
  toResolve
    .filter { it !in resolved }
    .mapNotNull { targets[it] }
    .filter { it.isResolved() }
    .forEach { it.resolve() }
}


fun String.isConstant() = this.matches(valueRegex)
fun String.isResolved() = this.isConstant() || this in resolved

fun String.getValue() = when (isConstant()) {
  true -> this.toShort()
  else -> requireNotNull(values[this]) { "Signal not yet resolved: $this" }
}


enum class Operation(val exec: (Short, Short) -> Short) {
  NOT({ it, _ -> it.inv() }),
  AND({ a, b -> a and b }),
  OR({ a, b -> a or b }),
  LSHIFT({ a, b -> (a.toInt().shl(b.toInt())).toShort() }),
  RSHIFT({ a, b -> (a.toInt().shr(b.toInt())).toShort() }),
  ASSIGN({ it, _ -> it })
}

data class Signal(
  val target: String,
  val operation: Operation,
  val left: String,
  val right: String = ""
                 ) {
  fun isResolved(): Boolean = when (operation) {
    Operation.ASSIGN, Operation.NOT -> left.isResolved()
    else -> left.isResolved() && right.isResolved()
  }

  private fun value() = when (operation) {
    Operation.ASSIGN, Operation.NOT -> operation.exec(left.getValue(), 0)
    else -> operation.exec(left.getValue(), right.getValue())
  }

  fun resolve() {
    resolved.add(target)
    values[target] = value()
  }
}
