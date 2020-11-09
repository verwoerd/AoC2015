package day21.part2

import day21.part1.Boss
import day21.part1.ScenarioResult
import day21.part1.generateScenarios
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/11/2020
 */
fun part2(input: BufferedReader): Any {
  val boss = Boss.readBoss(input)
  val hitPoints = 100
  return generateScenarios(hitPoints)
    .filter { it.resolveFight(boss) == ScenarioResult.BOSS }
    .maxByOrNull { it.cost }?.cost ?: error("no result")
}
