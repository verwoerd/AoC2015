package part2

import Coordinate
import withResourceFile
import java.io.BufferedReader
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.streams.asSequence

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader): Any {
  val lights = Array(1000) { IntArray(1000) }
  return input.lines().asSequence().map { parseLine(it) }.fold(lights) { current, it ->
    (it.start.y..it.end.y).forEach {y ->
      (it.start.x..it.end.x).forEach {x ->
        current[y][x] = it.instruction.execute(current[y][x])
      }
    }
    current
  }.map {line -> line.sum() }.sum()
}

fun parseLine(line: String): LightInstruction {
  val (instruction, remainder) = Instruction.fromInstruction(line)
  val (x1, y1, x2,y2) = remainder.split(","," through ").map{it.trim().toInt()}
  return LightInstruction(instruction = instruction, start = Coordinate(x1,y1), end = Coordinate(x2,y2))
}

enum class Instruction(
  private val exec: (Int) -> Int
                      ) {
  TURN_ON({ it+1 }), TOGGLE({ it +2 }), TURN_OFF({ max(it -1,0)});
  companion object {
    fun fromInstruction(line: String) = when {
      line.startsWith("turn on") -> TURN_ON to line.drop(7)
      line.startsWith("turn off") -> TURN_OFF to line.drop(8)
      line.startsWith("toggle") -> TOGGLE to line.drop(6)
      else -> error("Unknown input: $line")
    }
  }

  fun execute(current: Int) = exec(current)
}

data class LightInstruction(
  val instruction: Instruction,
  val start: Coordinate,
  val end: Coordinate
                           )
