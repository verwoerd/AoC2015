package part1

import ExampleTest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

/**
 * @author verwoerd
 * @since 06-11-20
 */
class Part1Test : ExampleTest(examples = 1, part = 1, wrapper())

fun wrapper() = fillToTarget(25)
