/**
 * @author verwoerd
 * @since 09-11-20
 */
fun Boolean.toInt() = when {
  this -> 1
  else -> 0
}
