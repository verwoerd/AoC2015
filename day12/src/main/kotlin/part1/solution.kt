package part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part1(input: BufferedReader) = input.readLine().split(Regex("[^-\\d]+")).filter { it.isNotEmpty() }.map { it.toLong() }.sum()

