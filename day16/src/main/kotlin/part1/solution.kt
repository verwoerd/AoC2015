package part1

import part1.Sue.Companion.fromString
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader): Any =
  input.lineSequence().map(::fromString)
    .filter { isNullOrValue(it.children, 3) }
    .filter { isNullOrValue(it.cats, 7) }
    .filter { isNullOrValue(it.samoyeds, 2) }
    .filter { isNullOrValue(it.pomeranians, 3) }
    .filter { isNullOrValue(it.akitas, 0) }
    .filter { isNullOrValue(it.vizslas, 0) }
    .filter { isNullOrValue(it.goldfish, 5) }
    .filter { isNullOrValue(it.trees, 3) }
    .filter { isNullOrValue(it.cars, 2) }
    .filter { isNullOrValue(it.perfumes, 1) }
    .joinToString("\n")


fun isNullOrValue(value: Int?, target: Int) = value == null || target == value

data class Sue(
  val number: Int,
  var children: Int? = null,
  var cats: Int? = null,
  var samoyeds: Int? = null,
  var pomeranians: Int? = null,
  var akitas: Int? = null,
  var vizslas: Int? = null,
  var goldfish: Int? = null,
  var cars: Int? = null,
  var trees: Int? = null,
  var perfumes: Int? = null,
  ) {
  companion object{
    private val regex = Regex("Sue (?<number>\\d+): (?<prop1>\\w+): (?<val1>\\d+), (?<prop2>\\w+): (?<val2>\\d+), (?<prop3>\\w+): (?<val3>\\d+)")
    fun fromString(line: String) : Sue {
      val (number, prop1, val1,prop2,val2,prop3,val3) = regex.matchEntire(line)!!.destructured
      val sue = Sue(number.toInt())
      sue.set(prop1,val1.toInt())
      sue.set(prop2,val2.toInt())
      sue.set(prop3,val3.toInt())
      return sue
    }
  }

  fun set(property: String, value: Int){
    when(property) {
      "children" -> this.children = value
      "cats" -> this.cats = value
      "samoyeds" -> this.samoyeds = value
      "pomeranians" -> this.pomeranians = value
      "akitas" -> this.akitas = value
      "vizslas" -> this.vizslas = value
      "goldfish" -> this.goldfish = value
      "trees" -> this.trees = value
      "cars" -> this.cars = value
      "perfumes" -> this.perfumes = value
      else -> {
        error("Invalid property: $property")
      }
    }
  }
}
