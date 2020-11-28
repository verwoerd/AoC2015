package day25.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 28/11/2020
 */
fun part1(input: BufferedReader): Any {
  val line = input.readLine()
  val (row, column) = regex.matchEntire(line)?.groupValues?.drop(1)?.map { it.toInt() } ?: error("parse error")
  val startColumnValue = (1 until row).fold(1, Int::plus)
  println("Looking for ($row, $column)")
  println("Column 1 has value $startColumnValue")
  val number = (2..column).fold(startColumnValue) {acc, l -> acc + l + row -1}

  println("we need $number from sequence")
  val sequence = generateSequence(20151125L) { previous ->
    seen.computeIfAbsent(previous) { previous.times(252533).rem(33554393) }
  }
  return sequence.elementAt(number -1)
}

val regex =
  Regex("To continue, please consult the code grid in the manual\\.  Enter the code at row (\\d+), column (\\d+)\\.")

val seen = mutableMapOf<Long, Long>()
