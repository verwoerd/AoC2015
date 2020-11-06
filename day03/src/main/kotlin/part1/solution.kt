package part1

import Coordinate
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader) = input.readLine().runningFold(origin) { acc, c -> acc.move(c) }.distinct().count()


fun Coordinate.move(c: Char) = when (c) {
  '<' -> plusX(-1)
  '>' -> plusX(1)
  '^' -> plusY(1)
  else -> plusY(-1)
}
