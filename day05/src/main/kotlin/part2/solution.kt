package part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader) =
  input.lines().map { line ->
    line.fold(NiceState()) { acc, c ->
      val doublePair = acc.pairs[acc.last]?.contains(c) ?: false
      acc.copy(
        second = acc.last,
        last = c,
        doublePair = acc.doublePair || doublePair,
        splitRepeat = acc.splitRepeat || (acc.last != c && acc.second == c),
        pairs = acc.pairs.also {
          it[acc.second] = it.getOrDefault(acc.second, mutableSetOf()).also { list -> list.add(acc.last) }
        },
              )
    }
  }.filter { it.isNice() }.count()


data class NiceState(
  var last: Char = '-',
  var second: Char = '-',
  var pairs: MutableMap<Char, MutableSet<Char>> = mutableMapOf(),
  var doublePair: Boolean = false,
  var splitRepeat: Boolean = false
                    ) {
  fun isNice() = doublePair && splitRepeat
}
