package part2

import part1.Sue
import part1.isNullOrValue
import withResourceFile
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader) =
  input.lineSequence().map(Sue.Companion::fromString)
    .filter { isNullOrValue(it.children, 3) }
    .filter { isNullOrGreater(it.cats, 7) }
    .filter { isNullOrValue(it.samoyeds, 2) }
    .filter { isNullOrSmaller(it.pomeranians, 3) }
    .filter { isNullOrValue(it.akitas, 0) }
    .filter { isNullOrValue(it.vizslas, 0) }
    .filter { isNullOrSmaller(it.goldfish, 5) }
    .filter { isNullOrGreater(it.trees, 3) }
    .filter { isNullOrValue(it.cars, 2) }
    .filter { isNullOrValue(it.perfumes, 1) }
    .joinToString("\n")


fun isNullOrGreater(value: Int?, target: Int) = value == null || value > target
fun isNullOrSmaller(value: Int?, target: Int) = value == null || value < target
