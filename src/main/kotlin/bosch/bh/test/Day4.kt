import java.io.File

class Day4 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day4_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 1514) { "expected 1514 but got $result1" }

    val data2 = File("src/main/resources/day4_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): Int {
    return lines.sumOf { line ->
      val (input, checksum, sectorID) = splitInput(line)
      val eachCount = input.groupingBy { it }.eachCount()
      val sorted = eachCount.entries
        .sortedWith(compareByDescending<Map.Entry<Char, Int>> { it.value }.thenBy { it.key })
        .map { it.key }
        .take(5)
        .joinToString("")

      val isValid = checksum == sorted
      if (!isValid) {
        println("INVALID: $line -> expected: $sorted, got: $checksum")
      }

      if (isValid) sectorID else 0
    }
  }

  private fun splitInput(line: String): Triple<String, String, Int> {
    val input = line.take(line.indexOf('[')).filter { c -> c.isLowerCase() && c.isLetter() }
    val checksum = line.substring(line.indexOf('[') + 1, line.indexOf(']'))
    val sectorStart = line.lastIndexOf('-') + 1
    val sectorEnd = line.indexOf('[')
    val sectorID = line.substring(sectorStart, sectorEnd).toInt()
    return Triple(input, checksum, sectorID)
  }

  private fun getEncryptedPassword(line: String ) : Pair<String,Int> {
    val sectorStart = line.lastIndexOf('-') + 1
    val sectorEnd = line.indexOf('[')
    val sectorID = line.substring(sectorStart, sectorEnd).toInt()
    return Pair(line.take(line.lastIndexOf('-')), sectorID)
  }

  private fun part2(lines: List<String>): Int {
    lines.forEach { line ->
      val (password, sectorID) = getEncryptedPassword(line)
      val decrypted = password.map { c ->
        when (c) {
          '-' -> ' '
          ' ' -> ' '
          else -> {
            var newC = c
            repeat(sectorID % 26) { // Use modulo for efficiency
              newC = if (newC == 'z') 'a' else newC + 1
            }
            newC
          }
        }
      }.joinToString("")
      println(": $decrypted")
      if(decrypted.contains("northpole")) {
        return sectorID
      }
    }
    return -1
  }
}

fun main() {
  Day4().start()
}