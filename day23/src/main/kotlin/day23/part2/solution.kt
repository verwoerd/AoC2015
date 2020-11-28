package day23.part2

import day23.part1.InstructionLine
import day23.part1.Interpreter
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 28/11/2020
 */
@ExperimentalUnsignedTypes
fun part2(input: BufferedReader): Any {
  val program = input.lineSequence().map { InstructionLine.parseLine(it) }.toList()
  val interpreter = Interpreter(a = 1U)
  interpreter.executeProgram(program)
  return interpreter.b
}

