package part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader) =
  input.lines().map { checkNice(it) }.filter { it.isNice() }.count()


fun Char.isVowel() = when (this) {
  'a', 'e', 'i', 'o', 'u' -> 1
  else -> 0
}

fun isNono(first: Char, second: Char) =
  (first == 'a' && second == 'b') ||
      (first == 'c' && second == 'd') ||
      (first == 'p' && second == 'q') ||
      (first == 'x' && second == 'y')

fun checkNice(line: CharSequence) = line.fold(NiceState()) { state, c ->
  state.copy(
    last = c,
    vowels = state.vowels + c.isVowel(),
    double = state.double || state.last == c,
    nono = state.nono || isNono(state.last, c)
            )
}

data class NiceState(
  var last: Char = '-',
  var vowels: Int = 0,
  var double: Boolean = false,
  var nono: Boolean = false
                    ) {
  fun isNice() = vowels >= 3 && double && !nono
}
