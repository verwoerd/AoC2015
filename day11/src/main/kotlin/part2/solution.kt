package part2

import part1.increment
import part1.isValidPassword
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader) = generateSequence(input.readLine().increment()) {
  it.increment()
}.filter { it.isValidPassword() }.drop(1).first()
