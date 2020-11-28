package day23.part1

import day23.part1.InstructionLine.Companion.parseLine
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 28/11/2020
 */
@ExperimentalUnsignedTypes
fun part1(input: BufferedReader): Any {
  val program = input.lineSequence().map { parseLine(it) }.toList()
  val interpreter = Interpreter()
  interpreter.executeProgram(program)
  return interpreter.b
}

enum class Instruction {
  HLF, TPL, INC, JMP, JIE, JIO
}

data class InstructionLine(
  val instruction: Instruction,
  val register: Char = ' ',
  val offset: Int = 0
                          ) {
  companion object {
    fun parseLine(line: String): InstructionLine = when (val instruction = Instruction.valueOf(line.take(3).toUpperCase())) {
      Instruction.HLF, Instruction.TPL, Instruction.INC -> InstructionLine(instruction, line.drop(4).first())
      Instruction.JMP -> InstructionLine(instruction, offset = line.drop(4).toInt())
      else -> InstructionLine(instruction, line.drop(4).first(), line.drop(7).toInt())
    }
  }
}

@ExperimentalUnsignedTypes
data class Interpreter(
  var pc: Int = 0,
  var a: UInt = 0U,
  var b: UInt = 0U
                      ) {

  fun executeProgram(program: List<InstructionLine>) {
    while(pc in program.indices) {
      val current  = program[pc]
      when(current.instruction) {
        Instruction.HLF -> setRegister(current.register, getRegister(current.register)/2U).also { pc++ }
        Instruction.TPL -> setRegister(current.register, getRegister(current.register)*3U).also { pc++ }
        Instruction.INC -> setRegister(current.register, getRegister(current.register).inc()).also { pc++ }
        Instruction.JMP -> pc += current.offset
        Instruction.JIE -> when(getRegister(current.register).rem(2U)) {
          1U -> pc++
          else -> pc += current.offset
        }
        Instruction.JIO -> when(getRegister(current.register)) {
          1U -> pc += current.offset
          else -> pc++
        }
      }
    }
  }

  fun setRegister(name: Char, value: UInt) {
    when(name) {
      'a' -> a = value
      'b' -> b = value
      else -> error("Invalid register")
    }
  }

  fun getRegister(name: Char) = when(name) {
    'a'->a
    'b'->b
    else -> error("invlaid register")
  }

}
