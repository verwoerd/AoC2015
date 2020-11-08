package day20.part2

import java.io.BufferedReader
import kotlin.math.sqrt

/**
 * @author verwoerd
 * @since 08/11/2020
 */
fun part2(input: BufferedReader): Any {
  val target = input.readLine().toLong()
  if (target <= 10) {
    return 1
  }
  val (upper, _) = generateSequence(3L to 44L) { (n, _) ->
    val nextHouse = 2 * n
    val presents = calculatePresents(nextHouse)
    (nextHouse to presents)
  }.find { it.second >= target }!!
  val (house, _) = generateSequence(upper / 2 to 0L) { (house, _) ->
    val nextHouse = house + 2
    val presents = calculatePresents(nextHouse)
    nextHouse to presents
  }.find { it.second >= target }!!
  return house
}

fun calculatePresents(nextHouse: Long) = (1..sqrt(nextHouse.toDouble()).toLong()).filter { nextHouse.rem(it) == 0L }
  .flatMap { listOf(it, nextHouse / it) }.distinct()
  .filter { nextHouse / it <= 50 }
  .map { 11 * it }.sum()
