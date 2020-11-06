package part1

import java.io.BufferedReader
import kotlin.math.max

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader): Long {
  return input.lines().map {line ->
    val(l,w,h) = parseInput(line)
   val surface = 2*l*w + 2*w*h + 2*h*l
   val extra  = when(max(l,max(w,h))) {
     l -> w*h
     w -> l*h
     else -> l*w
   }
   extra + surface
 }.reduce(Long::plus).get()
}

fun parseInput(line: String) =line.splitToSequence('x').map { it.toLong() }.take(3).toList()
