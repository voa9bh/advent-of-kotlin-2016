package bosch.bh.test

import java.security.MessageDigest
import kotlin.collections.get
import kotlin.text.toInt

class Day5 {
  fun start() {
    val result1 = part1("abc")
    println("result1 = $result1")
    check(result1 == "18f47a30") { "expected 18f47a30 but got $result1" }

    val result2 = part1("ugkcyxxp")
    println("result2 = $result2")

//    val result3 = part2(data1)
//    println("result3 = $result3")
//
//    check(result3 == 1) { "expected 1 but got $result3" }
//
//    val result4 = part2(data2)
//    println("result4 = $result4")
  }

  private fun part1(input: String): String {
    var code = ""
    var counter = 0
    val md = MessageDigest.getInstance("MD5")

    repeat(8) {
      while (true) {
        val testString = "$input$counter"
        md.reset() // Reset instead of creating new instance
        val digest = md.digest(testString.toByteArray())
        counter++

        // Check for "00000" prefix directly on bytes (faster)
        if (digest[0] == 0.toByte() &&
          digest[1] == 0.toByte() &&
          (digest[2].toInt() and 0xF0) == 0) {

          // Convert only the 6th character to hex
          val sixthChar = (digest[2].toInt() and 0x0F).toString(16)
          code += sixthChar
          println(code)
          break
        }
      }
    }
    return code
  }

  private fun part2(lines: List<String>): Int {
    return 5
  }

}

fun main() {
  Day5().start()
}