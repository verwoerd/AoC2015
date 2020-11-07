package part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader) = generateSequence(input.readLine().increment()) {
  it.increment()
}.find { it.isValidPassword() } ?: error("No valid next string found")


tailrec fun CharSequence.increment(): String {
  val (result, last) = foldRight(mutableListOf<Char>() to 'a') { c, (result, previous) ->
    when (c) {
      'i', 'o', 'l' -> mutableListOf<Char>().also { list ->
        repeat(result.size) { list.add('a') }
        list.add(c.increment())
      } to c.increment()
      else -> when (previous) {
        'a' -> {
          val next = c.increment()
          result.also { it.add(next) } to next
        }
        else -> result.also { it.add(c) } to '-'
      }
    }
  }
  val seq = result.reversed().joinToString("")
  return if (last != 'a') seq
  else seq.increment()
}

fun Char.increment(): Char = when (val next = this + 1) {
  'i', 'o', 'l' -> next.increment()
  in 'a'..'z' -> next
  else -> 'a'
}


fun CharSequence.isValidPassword() = fold(PasswordState()) { acc, c ->
  PasswordState(
    previous = c,
    second = acc.previous,
    increasingStreet = acc.isIncreasingStreet(c),
    noIllegalCharacters = acc.noIllegalCharacters(c),
    seenPairs = acc.isOverlappingPair(c)
               )
}.isValid()


data class PasswordState(
  var previous: Char = '-',
  var second: Char = '-',
  var increasingStreet: Boolean = false,
  var noIllegalCharacters: Boolean = true,
  var seenPairs: MutableSet<Char> = mutableSetOf()
                        ) {
  fun isIncreasingStreet(c: Char) = increasingStreet || (c - previous == 1 && c - second == 2)

  fun noIllegalCharacters(c: Char) = noIllegalCharacters && c != 'i' && c != 'o' && c != 'l'

  fun isOverlappingPair(c: Char) = seenPairs.also {
    if(c == previous){
      it.add(c)
    }
  }

  fun isValid() = increasingStreet && noIllegalCharacters && seenPairs.size > 1
}
