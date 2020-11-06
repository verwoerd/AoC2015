package part2

import origin
import part1.move
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader) = input.readLine().runningFoldIndexed(origin to origin) { index, (santa, robot), c ->
  when (index.rem(2)) {
    0 -> santa.move(c) to robot
    else -> santa to robot.move(c)
  }
}.flatMap { it.toList() }.distinct().count()


