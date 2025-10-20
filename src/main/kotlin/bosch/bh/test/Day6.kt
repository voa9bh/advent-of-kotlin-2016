import java.io.File

class Day6 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day6_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == "easter") { "expected easter but got $result1" }

    val data2 = File("src/main/resources/day6_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == "advent") { "expected advent but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): String {
    val sb:StringBuilder=StringBuilder()

    val width = lines.maxBy { line -> line.length }.length

    for(x in 0..<width) {
      val map = lines.map { line -> line[x] }
      val eachCount = map.groupingBy { it }.eachCount()
      val maxBy = eachCount.maxBy { it.value }
      sb.append(maxBy.key)
    }
    return sb.toString()
  }

  private fun part2(lines: List<String>): String {
    val sb:StringBuilder=StringBuilder()

    val width = lines.maxBy { line -> line.length }.length

    for(x in 0..<width) {
      val map = lines.map { line -> line[x] }
      val eachCount = map.groupingBy { it }.eachCount()
      val maxBy = eachCount.minBy { it.value }
      sb.append(maxBy.key)
    }
    return sb.toString()
  }

}

fun main() {
  Day6().start()
}