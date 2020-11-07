import part1.part1
import part2.part2

/**
 * @author verwoerd
 * @since 06-11-20
 */
@ExperimentalUnsignedTypes
fun main() {
  withResourceFile(::part1)
  withResourceFile(::part2)
}
