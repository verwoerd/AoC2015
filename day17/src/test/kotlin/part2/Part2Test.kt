package part2

import ExampleTest


class Part2Test : ExampleTest(examples = 1, part = 2, wrapper())
fun wrapper() = fillTargetMinimalContainers(25)
