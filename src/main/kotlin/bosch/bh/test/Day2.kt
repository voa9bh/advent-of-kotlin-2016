package bosch.bh.test

import java.io.File

class Day2 {
  val keyPad1 = listOf("123", "456", "789")
  val keyPad2 = listOf(
    "  1  ",
    " 234 ",
    "56789",
    " ABC  ",
    "  D  "
  )

  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day2_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == "1985") { "expected 1985 but got $result1" }

    val data2 = File("src/main/resources/day2_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")
    check(result3=="5DB3") { "expected 5DB3 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part2(data1: List<String>) : String {
    var x = 1
    var y = 3

    var output = ""

    for (line in data1) {
      for (char in line) {
        var newX = x
        var newY = y
        when (char) {
          'U' -> newY = y - 1
          'D' -> newY = y + 1
          'L' -> newX = x - 1
          'R' -> newX = x + 1
        }
        newX = newX.coerceIn(0..4)
        newY = newY.coerceIn(0..4)
        if(keyPad2[newY][newX]!=' ') {
          x = newX
          y = newY
        }
      }
      output += keyPad2[y][x]
    }

    return output
  }

  private fun part1(data1: List<String>): String {
    var x = 1
    var y = 1

    var output = ""

    for (line in data1) {
      for (char in line) {
        when (char) {
          'U' -> y--
          'D' -> y++
          'L' -> x--
          'R' -> x++
        }
        x = x.coerceIn(0..2)
        y = y.coerceIn(0..2)
      }
      output += keyPad1[y][x]
    }

    return output
  }
}

fun main() {
  Day2().start()
}