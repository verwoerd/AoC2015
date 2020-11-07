package part2

import nonRepeatingPermutations
import part1.HappinessRecord
import part1.findMax
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader): Any {
  val targets = input.lineSequence().map(HappinessRecord.Companion::fromString).toMutableList()
  targets.map { it.owner }.distinct().flatMap { listOf(HappinessRecord("me", it, 0), HappinessRecord(it, "me", 0)) }
    .toCollection(targets)
  val connections = targets.groupBy { it.owner }
  val combinations = nonRepeatingPermutations(connections.size, connections.keys)
  return findMax(combinations, connections)
}
