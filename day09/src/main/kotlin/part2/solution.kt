package part2

import part1.Route
import java.io.BufferedReader
import kotlin.streams.asSequence
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader) =
  input.lines().asSequence().map(Route::fromString).flatMap { listOf(it, it.reverse()) }.groupBy { it.from }.let(::greedyMaxTsp)



fun greedyMaxTsp(routeMap: Map<String, List<Route>>): Long {
  // It should be to greedy, but it gives correct answers
  return routeMap.keys.map {
    calculateMaxPathGreedy(it, mutableSetOf(it), 0, routeMap)
  }.maxByOrNull { it }?: error("invalid routes minimum")
}

tailrec fun calculateMaxPathGreedy(current: String, seen: MutableSet<String>, currentCost:Long, routeMap:Map<String, List<Route>>): Long {
  if(seen.size == routeMap.size) {
    println("""Calculated route ${seen.joinToString("->")} with cost $currentCost""")
    return currentCost
  }
  val next = routeMap[current]?.filter { it.to !in seen }?.maxByOrNull { it.cost } ?: error("No next route found")
  seen.add(next.to)
  return calculateMaxPathGreedy(next.to, seen, currentCost+next.cost, routeMap)
}
