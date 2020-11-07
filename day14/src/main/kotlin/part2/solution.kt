package part2

import part1.Reindeer
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */

fun part2(input: BufferedReader) = newScore(2503)(input)


fun newScore(time: Int) = { input: BufferedReader ->
  val reindeers = input.lineSequence().map(Reindeer.Companion::fromString).toList()
  val scores = reindeers.map { it to 0 }.toMap().toMutableMap()
  var current = 1
  while (current < time) {
    val positions = scores.keys.map { it to it.distance(current) }.sortedByDescending { it.second }
    var nextUpdate = scores.keys.map { it.nextChange(current) }.minOrNull()!!
    if (current + nextUpdate > time) {
      nextUpdate = time - current
    }
    positions.filter { it.second == positions.first().second }
      .forEach { scores[it.first] = scores[it.first]!! + nextUpdate }
    current += nextUpdate
  }
  scores.values.maxOrNull()!!
}


fun Reindeer.nextChange(current: Int): Int {
  val cycle = period + rest
  return when (val remainder = current.rem(cycle)) {
    in 0..period -> 1
    else -> cycle - remainder
  }
}
