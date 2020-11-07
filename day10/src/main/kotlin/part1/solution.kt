package part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(reader: BufferedReader): Any {
  var input = reader.readLine()
  repeat(40) {
    input = doPart1(input)
  }
  return input.length
}

fun doPart1(input: String): String =
  input.fold(CountState()) { acc, c ->
    acc.takeNext(c)
  }.takeNext('-').sequence.joinToString("")


fun Int.toCharValue() = '0' + this

data class CountState(
  val lastSeen: Char = '-',
  val count: Int = 0,
  val sequence: MutableList<Char> = mutableListOf()
                     ) {
  fun takeNext(c: Char) = when (c) {
      lastSeen -> copy(count = count + 1)
      else -> CountState(lastSeen = c, count = 1, sequence = sequence.also {
        if(lastSeen != '-') {
          it.add(count.toCharValue())
          it.add(lastSeen)
        }
      })
    }

}
