package day24.part1

import java.io.BufferedReader
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 28/11/2020
 */
fun part1(input: BufferedReader): Any {
  val presents = input.lines().map { it.toLong() }.toList()
  return balancingAct(presents)
}

fun balancingAct(presents: List<Long>, compartments: Int = 3): Long {
  val target = presents.sum() / compartments
  var i = 1
  while (i in presents.indices) {
    val options = presents.combinations(i, target).filter { it.sum() == target }
    if (options.isNotEmpty()) {
      return options.minOf { list -> list.reduce(Long::times) }
    }
    i++
  }
  error("No solution found")
}

fun List<Long>.combinations(
  target: Int,
  stop: Long,
  index: Int = 0,
  current: List<Long> = mutableListOf()
                           ): List<List<Long>> {
  if (current.sum() > stop) return emptyList()
  if (index >= size) {
    return if (current.size != target) emptyList() else listOf(current)
  }
  if (current.size == target) {
    return listOf(current)
  }
  return combinations(target, stop, index + 1, current) + combinations(target, stop, index + 1, current + get(index))
}
