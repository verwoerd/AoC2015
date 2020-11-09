package day21.part1

import day21.part1.Boss.Companion.readBoss
import toInt
import java.io.BufferedReader
import kotlin.Int.Companion.MAX_VALUE

/**
 * @author verwoerd
 * @since 08/11/2020
 */
fun part1(input: BufferedReader): Any {
  val boss = readBoss(input)
  val hitPoints = 100
  val best =  generateScenarios(hitPoints)
    .filter { it.resolveFight(boss) == ScenarioResult.PLAYER }
    .minByOrNull { it.cost } ?: error("no result")
  return best.cost
}

fun generateScenarios(hitPoints: Int) =weaponList.asSequence()
  .flatMap { weapon -> armorList.map { weapon to it } }
  .flatMap { (weapon, armor) -> ringList.dropLast(1).map { Triple(weapon, armor, it) } }
  .flatMap { (weapon, armor, ring1) ->
    ringList.drop(1).filter { ring1 != it }.map { Scenario(hitPoints, weapon, armor, ring1, it) }
  }

data class Weapon(
  val name: String,
  val cost: Int,
  val damage: Int
                 )

val weaponList = listOf(
  Weapon("Dagger", 8, 4),
  Weapon("Shortsword", 10, 5),
  Weapon("Warhammer", 25, 6),
  Weapon("Longsword", 40, 7),
  Weapon("Greataxe", 74, 8)
                       )

data class Armor(
  val name: String,
  val cost: Int,
  val armor: Int
                )

val armorList = listOf(
  Armor("(none)", 0, 0),
  Armor("Leather", 13, 1),
  Armor("Chainmail", 31, 2),
  Armor("Splintmail", 53, 3),
  Armor("Bandedmail", 75, 4),
  Armor("Platemail", 102, 5)
                      )

data class Ring(
  val name: String,
  val cost: Int,
  val damage: Int,
  val armor: Int
               )

val ringList = listOf(
  Ring("(slot 1 empty)", 0, 0, 0),
  Ring("Damage +1", 25, 1, 0),
  Ring("Damage +2", 50, 2, 0),
  Ring("Damage +3", 100, 3, 0),
  Ring("Defense +1", 20, 0, 1),
  Ring("Defense +2", 40, 0, 2),
  Ring("Defense +3", 80, 0, 3),
  Ring("(slot 2 empty)", 0, 0, 0)
                     )

val ring1Empty = Ring("(slot 1 empty)", 0, 0, 0)
val ring2Empty = Ring("(slot 2 empty)", 0, 0, 0)

data class Boss(
  val hitPoints: Int,
  val armor: Int,
  val damage: Int
               ) {
  companion object {
    fun readBoss(input: BufferedReader): Boss {
      val hp = input.readLine().split(": ").last().toInt()
      val damage = input.readLine().split(": ").last().toInt()
      val armor = input.readLine().split(": ").last().toInt()
      return Boss(hp, armor, damage)
    }
  }

}

enum class ScenarioResult {
  PLAYER, TIE, BOSS
}

data class Scenario(
  val hitPoints: Int,
  val weapon: Weapon,
  val armor: Armor,
  val ring1: Ring,
  val ring2: Ring
                   ) {
  val cost: Int by lazy { weapon.cost + armor.cost + ring1.cost + ring2.cost }
  val damage: Int by lazy { weapon.damage + ring1.damage + ring2.damage }
  val ac: Int by lazy { armor.armor + ring1.armor + ring2.armor }

  fun resolveFight(boss: Boss): ScenarioResult {
    val bossRounds = getRoundsToDeath(boss.damage)
    val playerRounds = getRoundsToKill(boss.hitPoints, boss.armor)
    return when {
      playerRounds == MAX_VALUE && bossRounds == MAX_VALUE -> ScenarioResult.TIE
      playerRounds <= bossRounds -> ScenarioResult.PLAYER
      else -> ScenarioResult.BOSS
    }
  }


  fun getRoundsToKill(hp: Int, armor: Int) = when (val dps = damage - armor) {
    in 1..MAX_VALUE -> hp / (dps) + (hp.rem(dps) > 0).toInt()
    else -> MAX_VALUE
  }

  fun getRoundsToDeath(damage: Int) = when (val dps = damage - ac) {
    in 1..MAX_VALUE -> hitPoints / dps + (hitPoints.rem(dps) > 0).toInt()
    else -> MAX_VALUE
  }

}
