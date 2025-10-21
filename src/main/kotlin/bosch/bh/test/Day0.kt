import java.io.File

class Day0 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day0_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 2) { "expected 2 but got $result1" }

    val data2 = File("src/main/resources/day0_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 3) { "expected 3 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): Int {
    return 5
  }

  private fun part2(lines: List<String>): Int {
    return 5
  }

}

fun main() {
  Day0().start()
}