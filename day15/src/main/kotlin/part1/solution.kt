package part1

import withResourceFile
import java.io.BufferedReader
import kotlin.math.max

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader): Any {
  val ingredients = input.lineSequence().map(Ingredient.Companion::fromString).toList()
  return bakeCookie(ingredients, 100, Ingredient()).maxByOrNull { it.value() }!!.value()
}

fun bakeCookie(ingredients:List<Ingredient>, spoonsLeft: Int, cookie: Ingredient): Sequence<Ingredient> {
  if(ingredients.isEmpty() || spoonsLeft == 0) {
    return sequenceOf(cookie)
  }
  val ingredient = ingredients.first()
  if(ingredients.size == 1) {
    return bakeCookie(ingredients.drop(1), 0, cookie.add(ingredient, spoonsLeft))
  }
  return  (0..spoonsLeft).asSequence().flatMap {
    bakeCookie(ingredients.drop(1), spoonsLeft-it, cookie.add(ingredient, it))
  }
}



data class Ingredient(
  val name: String = "Cookie",
  val capacity: Int = 0,
  val durability: Int=0,
  val flavor: Int = 0,
  val texture: Int =0 ,
  val calories: Int=0
                     ) {
  companion object{
    private val regex =  Regex("(?<name>\\w+): capacity (?<capacity>-?\\d+), durability (?<durability>-?\\w+), flavor (?<flavor>-?\\w+), texture (?<texture>-?\\w+), calories (?<calories>-?\\w+)")
    fun fromString(line: String) : Ingredient {
      val ( name, capacity,durability,flavor,texture,calories) = regex.matchEntire(line)?.destructured ?:error("Line did not match $line")
      return Ingredient(name,capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(),calories.toInt())
    }
  }

  fun add(ingredient: Ingredient, times: Int) : Ingredient = copy(
    name= "$name ${ingredient.name}: $times",
    capacity = capacity + ingredient.capacity*times,
    durability = durability +ingredient.durability*times,
    flavor = flavor+ingredient.flavor*times,
    texture = texture+ingredient.texture*times,
    calories = calories+ingredient.calories*times
                                                                     )

  fun value() = max(0,capacity)* max(0, durability)*max(0,flavor)*max(0,texture)
}
