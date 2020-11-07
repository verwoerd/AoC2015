package part2

import part1.doPart1
import withResourceFile
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(reader: BufferedReader): Any {
  var input = reader.readLine()
  repeat(50) {
    input = doPart1(input)
  }
  return input.length
}
