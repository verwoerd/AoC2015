package day22.part2


import day22.part1.Boss
import day22.part1.Player
import day22.part1.Scenario
import java.io.BufferedReader
import java.util.LinkedList
import kotlin.math.min

/**
 * @author verwoerd
 * @since 13/11/2020
 */
fun part2(input: BufferedReader): Any {
  val boss = Boss.readBoss(input)
  val player = Player()//hitPoints = 10, mana=250)
  val initialScenario = Scenario(0, 0, player, boss)
  val cases = LinkedList<Scenario>()
  var bestMana = Int.MAX_VALUE
  cases.add(initialScenario)
  while (cases.isNotEmpty()) {
    val current = cases.removeFirst()
    val results = current.createNextRound(true)
    val defeats = results.filter { it.boss.hitPoints <= 0 }
    if (defeats.isNotEmpty()) {
      bestMana = min(bestMana, defeats.minOf { it.mana })
      /*defeats.firstOrNull { it.mana == bestMana }?.let { println(it)
      println(it.spells.joinToString("\n", postfix = "\n---"))}*/
    }
    results.filter { it.boss.hitPoints > 0 }
      .filter { it.player.hitPoints > 0 }
      .filter { it.mana < bestMana }
      .toCollection(cases)
  }
  return bestMana
}
