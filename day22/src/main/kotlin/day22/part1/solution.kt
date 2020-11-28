package day22.part1


import day22.part1.Boss.Companion.readBoss
import toInt
import java.io.BufferedReader
import java.util.LinkedList
import kotlin.math.max
import kotlin.math.min

/**
 * @author verwoerd
 * @since 13/11/2020
 */
fun part1(input: BufferedReader): Any {
  val boss = readBoss(input)
  val player = Player()
  val initialScenario = Scenario(0, 0, player, boss)
  val cases = LinkedList<Scenario>()
  var bestMana = Int.MAX_VALUE
  cases.add(initialScenario)
  while (cases.isNotEmpty()) {
    val current = cases.removeFirst()
    val results = current.createNextRound()
    val defeats = results.filter { it.boss.hitPoints <= 0 }
    if (defeats.isNotEmpty()) {
      bestMana = min(bestMana, defeats.minOf { it.mana })
    }
    results.filter { it.boss.hitPoints > 0 }
      .filter { it.player.hitPoints > 0 }
      .filter { it.mana < bestMana }
      .toCollection(cases)
  }
  return bestMana
}


enum class Effect(
  val duration: Int,
  val armor: Int = 0,
  val damage: Int = 0,
  val mana: Int = 0
                 ) {
  NONE(0),
  SHIELD(6, armor = 7),
  POISON(6, damage = 3),
  RECHARGE(5, mana = 101)
}

enum class Spell(
  val mana: Int,
  val damage: Int = 0,
  val heal: Int = 0,
  val effect: Effect = Effect.NONE
                ) {
  NONE(0),
  MAGIC_MISSILE(53, damage = 4),
  DRAIN(73, damage = 2, heal = 2),
  SHIELD(113, effect = Effect.SHIELD),
  POISON(173, effect = Effect.POISON),
  RECHARGE(229, effect = Effect.RECHARGE)
}

data class Boss(
  val hitPoints: Int,
  val damage: Int,
  val poison: Int = 0
               ) {
  companion object {
    fun readBoss(input: BufferedReader): Boss {
      val hp = input.readLine().split(": ").last().toInt()
      val damage = input.readLine().split(": ").last().toInt()
      return Boss(hp, damage)
    }
  }
}

data class Player(
  val hitPoints: Int = 50,
  val mana: Int = 500,
  val shield: Int = 0,
  val recharge: Int = 0
                 )

data class Scenario(
  val round: Int,
  val mana: Int = 0,
  val player: Player,
  val boss: Boss,
  val spells: List<Pair<Spell, Scenario>> = listOf()
                   ) {
  fun createNextRound(hardmode:Boolean = false): MutableList<Scenario> {
    val scenarios = mutableListOf<Scenario>()
    when (round.rem(2)) {
      0 -> {
        if (player.mana >= Spell.MAGIC_MISSILE.mana) {
          scenarios += doPlayerRound(Spell.MAGIC_MISSILE, hardmode)
        }
        if (player.mana >= Spell.DRAIN.mana) {
          scenarios += doPlayerRound(Spell.DRAIN, hardmode)
        }
        if (player.mana >= Spell.SHIELD.mana && player.shield <= 1) {
          scenarios += doPlayerRound(Spell.SHIELD, hardmode)
        }
        if (player.mana >= Spell.POISON.mana && boss.poison <= 1) {
          scenarios += doPlayerRound(Spell.POISON, hardmode)
        }
        if (player.mana >= Spell.RECHARGE.mana && player.recharge <= 1) {
          scenarios += doPlayerRound(Spell.RECHARGE, hardmode)
        }
      }
      else -> scenarios += doBossRound(hardmode)
    }
    return scenarios
  }

  fun doPlayerRound(spell: Spell, hardmode: Boolean): Scenario {
    val playerDamage = spell.damage + when {
      boss.poison > 0 -> Effect.POISON.damage
      else -> 0
    }
    val playerMana = player.mana - spell.mana + when {
      player.recharge > 0 -> Effect.RECHARGE.mana
      else -> 0
    }
    val nextPlayer = Player(
      hitPoints = player.hitPoints + spell.heal - hardmode.toInt(),
      mana = playerMana, shield = when (spell) {
        Spell.SHIELD -> spell.effect.duration
        else -> max(player.shield - 1, 0)
      }, recharge = when (spell) {
        Spell.RECHARGE -> spell.effect.duration
        else -> max(player.recharge - 1, 0)
      }
                           )
    val nextBoss = boss.copy(
      hitPoints = boss.hitPoints - playerDamage,
      poison = when (spell) {
        Spell.POISON -> spell.effect.duration
        else -> max(boss.poison - 1, 0)
      }
                            )
    return Scenario(
      round + 1,
      mana + spell.mana,
      nextPlayer, nextBoss, spells + (spell to this.copy(spells = emptyList()))
                   )
  }

  fun doBossRound(hardmode: Boolean): Scenario {
    val playerDamage = when {
      boss.poison > 0 -> Effect.POISON.damage
      else -> 0
    }
    val playerMana = player.mana + when {
      player.recharge > 0 -> Effect.RECHARGE.mana
      else -> 0
    }

    val bossDamage = boss.damage - when {
      player.shield > 0 -> Effect.SHIELD.armor
      else -> 0
    } + hardmode.toInt()

    val nextPlayer = Player(
      hitPoints = player.hitPoints - bossDamage,
      mana = playerMana, shield = max(player.shield - 1, 0), recharge = max(player.recharge - 1, 0),
                           )
    val nextBoss = boss.copy(
      hitPoints = boss.hitPoints - playerDamage, poison = max(boss.poison - 1, 0)
                            )
    return Scenario(round + 1, mana, nextPlayer, nextBoss, spells + (Spell.NONE to this.copy(spells = emptyList())))
  }
}
