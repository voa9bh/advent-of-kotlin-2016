package bosch.bh.test

import java.io.File

class Day3 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day3_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 1) { "expected 1 but got $result1" }

    val data2 = File("src/main/resources/day3_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 1) { "expected 1 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): Int {
    return lines.count { line ->
      val split = line.trim().split("\\s+".toRegex())
      val s1 = split[0].toInt()
      val s2 = split[1].toInt()
      val s3 = split[2].toInt()

      s1 + s2 > s3 && s2 + s3 > s1 && s3 + s1 > s2
    }
  }

  private fun part2(lines: List<String>): Int {
    check(lines.size % 3 == 0) { "Expected multiple of 3 lines, but got ${lines.size}" }
    return lines.chunked(3).sumOf { lineGroup ->
      val numbers = lineGroup.map { it.trim().split("\\s+".toRegex()).map(String::toInt) }

      (0..2).count { i ->
        val s1 = numbers[0][i]
        val s2 = numbers[1][i]
        val s3 = numbers[2][i]
        s1 + s2 > s3 && s2 + s3 > s1 && s3 + s1 > s2
      }
    }
  }
}

fun main() {
  Day3().start()
}