package part1

import withResourceFile
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader): Any {
  return input.readLine().map { when(it) {
    '(' -> 1
    ')' -> -1
    else -> error("invalid input $it")
  } }.sum()
}
