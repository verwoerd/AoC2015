package part2

import part1.doLightAnimation
import part1.print
import part1.readMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader) = input.run(doAnimations(100))

fun doAnimations(times: Int) = { input: BufferedReader ->
  var grid = readMap(input)
  val size = grid.size
  // break the lights
  grid = grid.mapIndexed { y, line ->
    line.mapIndexed { x, value ->
      when {
        x == 0 && y == 0 -> true
        x == 0 && y == size - 1 -> true
        x == line.size - 1 && y == 0 -> true
        x == line.size - 1 && y == size - 1 -> true
        else -> value
      }
    }
  }
  repeat(times) {
    grid = grid.doBrokenLightAnimation()
  }
  grid.sumBy { line -> line.count { it } }
}

fun List<List<Boolean>>.doBrokenLightAnimation(): List<List<Boolean>> = mapIndexed { y, line ->
  line.mapIndexed { x, value ->
    when {
      x == 0 && y == 0 -> true
      x == 0 && y == size - 1 -> true
      x == line.size - 1 && y == 0 -> true
      x == line.size - 1 && y == size - 1 -> true
      else -> doLightAnimation(x, y, line.size, value)
    }
  }
}


