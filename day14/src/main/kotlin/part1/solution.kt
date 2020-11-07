package part1

import withResourceFile
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader)= maxDistance(2503)(input)

fun maxDistance(time: Int) = {input:BufferedReader  ->input.lineSequence().map(Reindeer.Companion::fromString).map { it.distance(time) }.maxOrNull()!!}


data class Reindeer(
  val name: String,
  val speed: Int,
  val period: Int,
  val rest: Int
                   ) {
  companion object{
    private val regex = Regex("(?<name>\\w+) can fly (?<speed>\\d+) km/s for (?<period>\\d+) seconds, but then must rest for (?<rest>\\d+) seconds.")
    fun fromString(line: String): Reindeer {
      val(_,name,speed,period,rest) = regex.matchEntire(line)?.groupValues ?: error("Parse error $line")
      return Reindeer(name,speed.toInt(),period.toInt(), rest.toInt())
    }
  }

  fun distance(time: Int): Int {
    val cycle = period+rest
    val cycles = time / cycle
    val remainder = time.rem(cycle)
    return  speed*period*cycles +  speed * when(remainder) {
      in 0..period-> remainder
      else -> period
    }

  }
}
