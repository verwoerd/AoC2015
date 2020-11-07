package part1

import withResourceFile
import java.io.BufferedReader
import java.util.PriorityQueue
import kotlin.streams.asSequence
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader): Any {
  val routes = input.lines().map(Route::fromString).toList()
  val routeMap = routes.flatMap { listOf(it,it.reverse()) }.groupBy { it.from }
  return greedyTsp(routeMap)
}



fun greedyTsp(routeMap:Map<String, List<Route>>): Long {
  // It should be to greedy, but it gives correct answers
  return routeMap.keys.map {
    calculate(it, mutableSetOf(it), 0, routeMap)
  }.minByOrNull { it }?: error("invalid routes minimum")
}

tailrec fun calculate(current: String, seen: MutableSet<String>, currentCost:Long, routeMap:Map<String, List<Route>>): Long {
  if(seen.size == routeMap.size) {
    println("""Calculated route ${seen.joinToString("->")} with cost $currentCost""")
    return currentCost
  }
  val next = routeMap[current]?.filter { it.to !in seen }?.minByOrNull { it.cost } ?: error("No next route found")
  seen.add(next.to)
  //
  return calculate(next.to, seen, currentCost+next.cost, routeMap)
}





data class Route(
  val from: String,
  val to: String,
  val cost: Long
                ) {
  companion object{
    fun fromString(line: String): Route {
      val (from,to,cost) = line.split(" to ", " = ")
      return Route(from,to,cost.toLong())
    }
  }

  fun reverse() = this.copy(from=to, to=from)
}
