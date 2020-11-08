package day20.part1

import java.io.BufferedReader
import kotlin.math.sqrt

/**
 * @author verwoerd
 * @since 08/11/2020
 */
fun part1(input: BufferedReader): Any {
  val target = input.readLine().toLong()
  if (target <= 10) {
    return 1
  }
  val (upper, _) = generateSequence(Triple(3L, 40L, listOf(1L, 2L, 3L))) { (n, _, list) ->
    val nextHouse = 2 * n
    val presents = (1..sqrt(nextHouse.toDouble()).toLong()).filter { nextHouse.rem(it) == 0L }
      .flatMap { listOf(it, nextHouse / it) }.distinct()
      .map { (10 * nextHouse / it) }.sum()
    Triple(nextHouse, presents, list + 2 * n)
  }.find { it.second >= target }!!
  val (house, _) = generateSequence(upper / 2 to 0L) { (house, _) ->
    val nextHouse = house + 2
    val presents = (1..sqrt(nextHouse.toDouble()).toLong()).filter { nextHouse.rem(it) == 0L }
      .flatMap { listOf(it, nextHouse / it) }.distinct()
      .map { (10 * nextHouse / it) }.sum()
    nextHouse to presents
  }.find { it.second >= target }!!
  return house
}
