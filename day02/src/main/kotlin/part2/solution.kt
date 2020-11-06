package part2

import part1.parseInput
import withResourceFile
import java.io.BufferedReader
import java.lang.Long.max

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader): Any {
  return input.lines().map { line ->
    val (l, w, h) = parseInput(line)
    val around = when(max(l,max(w,h))) {
      l -> w*2+h*2
      w -> l*2+h*2
      else -> l*2+w*2
    }
    val bow = l*w*h
    around+bow
  }.reduce(Long::plus).get()
}
