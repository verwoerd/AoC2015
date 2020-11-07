package part2

import java.io.BufferedReader
import java.util.Stack
import java.util.StringTokenizer

/**
 * @author verwoerd
 * @since 06-11-20
 */
fun part2(input: BufferedReader): Int {
  val tokenizer = StringTokenizer(input.readLine(), "[]}{,:", true)
  var realSum = 0
  var objectDepht = 0
  var arrayDepth = 0
  var redDepth = 0
  var isValue = false
  var isRed = false
  var sum = 0
  val stack = Stack<Int>()

  while (tokenizer.hasMoreTokens()) {
    val token = tokenizer.nextToken()
    when {
      token == "{" -> {
        stack.push(sum)
        sum = 0
        objectDepht++
      }
      token == "}" -> {
        if (!isRed) {
          sum += stack.pop()
        } else {
          sum = stack.pop()
        }
        objectDepht--
        if (objectDepht == 0) {
          realSum += sum
        }
      }
      token == "[" -> {
        arrayDepth++
        isValue = false
      }
      token == "]" -> arrayDepth--
      token == ":" -> isValue = true
      isValue && token.startsWith('"') -> {
        isValue = false
        if (!isRed && token == "\"red\"") {
          redDepth = objectDepht
          isRed = true
        }
      }
      !isRed && token.toIntOrNull() != null -> sum += token.toInt()
      isValue -> isValue = false
    }
    if (objectDepht < redDepth) {
      redDepth = 0
      isRed = false
    }
  }
  return realSum
}


