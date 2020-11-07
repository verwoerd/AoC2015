package part2

import part1.Operation
import part1.Signal
import part1.count
import part1.dependencies
import part1.parseLine
import part1.process
import part1.resolved
import part1.targets
import part1.values
import java.io.BufferedReader
import kotlin.streams.asSequence

/**
 * @author verwoerd
 * @since 06-11-20
 */
@ExperimentalUnsignedTypes
fun part2(input: BufferedReader): Any {
  val a = values["a"]!!
  println("Calculated a $a")
  targets.clear()
  dependencies.clear()
  resolved.clear()
  values.clear()
  targets["b"] = Signal(target="b", left = a.toString(), operation = Operation.ASSIGN)
  values["b"] = a
  resolved.add("b")
  resolved.add(a.toString())
  count = 2
  input.lines().asSequence().filter{!it.endsWith("-> b")}.forEach(::parseLine)
  process()
  return values.toSortedMap().map { "${it.key}: ${it.value.toUShort()}" }.joinToString("\n")
}
