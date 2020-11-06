package part1

import java.io.BufferedReader
import java.security.MessageDigest

/**
 * @author verwoerd
 * @since 06-11-20
 */
@ExperimentalUnsignedTypes
fun part1(input: BufferedReader): Any {
  val key= input.readLine()
  return generateSequence(1) { it +1 }.first {
    val value = key + it
    val output = md5(value).toUByteArray()
    val (first, second, third) = output
    first == UByte.MIN_VALUE&& second == UByte.MIN_VALUE && third < 16u
  }
}

fun md5(string: String): ByteArray = MessageDigest.getInstance("MD5").digest(string.toByteArray())

