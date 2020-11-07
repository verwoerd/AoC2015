package part1

import java.io.BufferedReader
import kotlin.streams.asSequence

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader): Any {
  val lines = input.readLines()
  val total = lines.sumBy { it.length }
  val real = lines.sumBy {
    var index = 1
    var count = 0
    val stop = it.length - 2
    while (index <= stop) {
      index += when (it[index]) {
        '\\' -> when (it[index + 1]) {
          '"', '\\' -> 2
          'x' -> 4
          else -> 1
        }
        else -> 1
      }
      count++
    }
    count
  }
  return total - real
}
