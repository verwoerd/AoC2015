package day19.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/11/2020
 */
fun part2(input: BufferedReader): Any {
  // Reverse engineer the medicine. This might be lucky that it works, since the unit test will fail for this method
  val (start, transformations) = readInput(input)
  var target = start
  var index = 0
  var skip = 0
  val maxSize = transformations.keys.maxByOrNull { it.count() }!!.size
  var iteration = 0
  // reverse engineer the steps
  while (index + skip < target.size) {
    // current window to view
    val size = when {
      target.dropLast(index + skip).last().isLowerCase() -> 2
      else -> 1
    }

    val search = target.dropLast(skip).takeLast(size + index)
    if (transformations.containsKey(search)) {
      //transformation formula exist, reduce the string to previous iteration
      target = target.dropLast(size + index + skip) + transformations.getValue(search) + target.takeLast(skip)
      index = 0
      skip = 0
      iteration++
      if (target.size == 1 && target.contains('e')) {
        // we found the medicine
        break
      }
    } else {
      // increase the search window
      index += size
      if (index + skip >= target.size || index > maxSize) {
        // search windown is to big, reduce it to the next char
        index = 0
        skip += when {
          target.dropLast(skip).last().isLowerCase() -> 2
          else -> 1
        }
      }
    }
  }
  return iteration
}

fun readInput(input: BufferedReader): Pair<List<Char>, Map<List<Char>, Sequence<Char>>> {
  val sequence = input.readLines()
  val transformations = sequence.take(sequence.size - 2).map { line ->
    val (from, to) = line.split(" => ")
    to.toList() to from
  }.groupBy { it.first }.mapValues { entry -> entry.value.map { it.second.asSequence() }.first() }

  val target = sequence.last()
  return target.toList() to transformations
}
