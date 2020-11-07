package part2

import part1.Ingredient
import part1.bakeCookie
import withResourceFile
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader): Any {
  val ingredients = input.lineSequence().map(Ingredient.Companion::fromString).toList()
  return bakeCookie(ingredients, 100, Ingredient()).filter { it.calories == 500 }.maxByOrNull { it.value() }!!.value()
}
