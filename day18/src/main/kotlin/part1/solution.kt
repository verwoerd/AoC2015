package part1

import Coordinate
import adjacentCircularCoordinates
import adjacentCoordinates
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader)=input.run(doAnimations(100))

fun doAnimations(times: Int)={input: BufferedReader->
  var grid  = readMap(input)
  repeat(times) {
    grid = grid.doLightAnimation()
  }
  grid.sumBy { line -> line.count { it } }
}

fun readMap(input: BufferedReader) = input.lineSequence().map{ line ->
  line.map { c ->
    when (c) {
      '#' -> true
      else -> false
    }
  }
}.toList()


fun List<List<Boolean>>.print() {
  println(joinToString("\n") { line ->
    line.joinToString(""){when(it){
    true -> "#"
    else -> "."
  } } })
}

fun List<List<Boolean>>.doLightAnimation(): List<List<Boolean>> = mapIndexed { y, line ->
    line.mapIndexed { x, value ->
      doLightAnimation(x,y, line.size,value)
    }
  }

fun  List<List<Boolean>>.doLightAnimation(x: Int, y: Int, maxX: Int, value: Boolean): Boolean {
  val neighbours = adjacentCircularCoordinates(Coordinate(x, y))
    .filter { it.x >= 0 && it.y >= 0 && it.x < maxX && it.y < size}
    .count { get(it.y)[it.x] }
  return when (value) {
    true -> neighbours in 2..3
    else -> neighbours == 3
  }
}
