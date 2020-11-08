package day19.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/11/2020
 */
fun part1(input: BufferedReader): Any {
  val (start, transformations) = readInput(input)
  val seen = mutableSetOf<List<Char>>()
  transformations.map { (key, values)->
    val result = mutableListOf<Char>()
    var index = 0
    val fingerSize = key.size
    while (index <= start.size - fingerSize) {
      val finger = start.drop(index).take(fingerSize)
      if(finger == key) {
        values.map { result + it.asSequence() + start.asSequence().drop(index+fingerSize) }.toCollection(seen)
      }
      result.add(finger.first())
      index++
    }
  }
  return seen.size
}

fun readInput(input: BufferedReader): Pair<List<Char>, Map<List<Char>, List<String>>> {
  val sequence = input.readLines()
  val transformations = sequence.take(sequence.size - 2).map { line ->
      val    (from, to) = line.split(" => ")
      from.toList() to to
  }.groupBy { it.first }.mapValues { entry -> entry.value.map { it.second } }

  val target = sequence.last()
  return target.toList() to transformations
}

