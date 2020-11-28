package day24.part2

import day24.part1.balancingAct
import java.io.BufferedReader
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 28/11/2020
 */
fun part2(input: BufferedReader): Any {
  val presents = input.lines().map { it.toLong() }.toList()
  return balancingAct(presents, 4)
}
