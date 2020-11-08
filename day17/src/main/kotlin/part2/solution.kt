package part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader) = fillTargetMinimalContainers(150)(input)

fun fillTargetMinimalContainers(target: Int) = { input: BufferedReader ->
  val containers = input.lineSequence().map { it.toInt() }.sorted().toList()
  containers.findAssignment(0, target, emptyList())
  val minimalContainers = solutions.minByOrNull { it.value.size > 0 }!!
  minimalContainers.value.size
}

val cache = Array(50) { IntArray(1000) }
val solutions = mutableMapOf<Int, MutableList<List<Int>>>().withDefault { mutableListOf() }

fun List<Int>.findAssignment(index: Int, target: Int, current: List<Int>): Int {
  if (target < 0) {
    return 0
  }
  if (index == size) {
    return when (target) {
      0 -> 1.also { solutions[current.size] = solutions.getValue(current.size).also { it.add(current) } }
      else -> 0
    }
  }
  // Removed cache so all solutions are visited
  cache[index][target] =
    findAssignment(index + 1, target, current) + findAssignment(index + 1, target - get(index), current + get(index))
  return cache[index][target]
}
