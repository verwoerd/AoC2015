package part2

import withResourceFile
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader): Any {
  return input.readLine().runningFold(0) { acc, c ->  when(c) {
    '(' -> acc + 1
    ')' -> acc -1
    else -> error("invalid input")
  } }.indexOf(-1)
}
