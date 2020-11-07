package part1

import ExampleTest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
class Part1Test : ExampleTest(examples = 4, part = 1, ::wrapper)

fun wrapper(input: BufferedReader) = doPart1(input.readLine())
