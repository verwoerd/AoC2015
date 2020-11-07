package part2

import java.io.BufferedReader
import kotlin.streams.asSequence

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader) = input.lines().asSequence().sumBy {
  it.sumBy { char ->
    when (char) {
      '"', '\\' -> 1
      else -> 0
    }
  } + 2
}
