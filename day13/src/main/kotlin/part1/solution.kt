package part1

import nonRepeatingPermutations
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader): Any {
  val targets = input.lineSequence().map(HappinessRecord.Companion::fromString).toList()
  val connections = targets.groupBy { it.owner }
  val combinations = nonRepeatingPermutations(connections.size, connections.keys)
  return findMax(combinations, connections)
}

fun findMax(combinations: List<List<String>>, connections: Map<String, List<HappinessRecord>>) =
  combinations.map { order ->
    order.fold(0 to order.last()) { (happiness, last), current ->
      (happiness + (connections[last] ?: error("")).find { it.person == current }!!.effect
          + (connections[current] ?: error("")).find { it.person == last }!!.effect
          ) to current
    }.first
  }.maxOrNull()!!


data class HappinessRecord(
  val owner: String,
  val person: String,
  val effect: Int
                          ) {
  companion object {
    private val regex = Regex("(\\w+) would (\\w+) (\\d+) happiness units by sitting next to (\\w+)\\.")

    private fun parseSign(sign: String) = when (sign) {
      "lose" -> -1
      else -> 1
    }

    fun fromString(line: String): HappinessRecord {
      val (_, owner, sign, value, target) = regex.matchEntire(line)!!.groupValues
      return HappinessRecord(owner = owner, person = target, effect = parseSign(sign) * value.toInt())
    }


  }

}
