package part2

import part1.md5
import withResourceFile
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
@ExperimentalUnsignedTypes
fun part2(input: BufferedReader): Any {
  val key= input.readLine()
  return generateSequence(1) { it +1 }.first {
    val value = key + it
    val output = md5(value).toUByteArray()
    val (first, second, third) = output
    first == UByte.MIN_VALUE&& second == UByte.MIN_VALUE && third== UByte.MIN_VALUE
  }
}
