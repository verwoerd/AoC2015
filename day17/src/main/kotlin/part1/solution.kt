package part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader) = fillToTarget(150)(input)


fun fillToTarget(target: Int) = { input: BufferedReader ->
  val containers = input.lineSequence().map { it.toInt() }.sorted().toList()
  containers.findAssignment(0, target)
}

val cache = Array(50) { IntArray(1000) }
val calculated = Array(50) { BooleanArray(1000) }

fun List<Int>.findAssignment(index: Int, target: Int): Int {
  if (target < 0) {
    return 0
  }
  if (index == size) {
    return when (target) {
      0 -> 1
      else -> 0
    }
  }

  if (calculated[index][target]) {
    return cache[index][target]
  }

  calculated[index][target] = true
  cache[index][target] = findAssignment(index + 1, target) + findAssignment(index + 1, target - get(index))
  return cache[index][target]
}

