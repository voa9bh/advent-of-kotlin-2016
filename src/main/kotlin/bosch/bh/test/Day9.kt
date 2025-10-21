import java.io.File

class Day9 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day9_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 51L) { "expected 51 but got $result1" }

    val data2 = File("src/main/resources/day9_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 50L) { "expected 3 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): Long {
    val sb = StringBuilder()

    lines.forEach { line ->
      val chars = line.toCharArray()
      var index = 0
      while (index < chars.size) {
        val char = chars[index]
        if (char != '(') {
          sb.append(char)
          index++
          continue
        }
        val closeParenIndex = line.indexOf(')', index)
        val formatContent = line.substring(index + 1, closeParenIndex)
        val parts = formatContent.split('x')
        val length = parts[0].toInt()
        val repeat = parts[1].toInt()

        // Get the characters to repeat
        val startPos = closeParenIndex + 1
        val endPos = startPos + length
        val toRepeat = line.substring(startPos, endPos)

        // Append the repeated characters
        repeat(repeat) {
          sb.append(toRepeat)
        }

        // Move index past the repeated section
        index = endPos
      }
      sb.append('\n')
    }

    println(sb.toString())
    return sb.chars().filter { !Character.isWhitespace(it) }.count()
  }

  private fun part2(lines: List<String>): Long {
    return lines.sumOf { line -> calculateDecompressedLength(line) }
  }

  private fun calculateDecompressedLength(input: String): Long {
    var total = 0L
    var index = 0

    while (index < input.length) {
      val char = input[index]
      if (char != '(') {
        total++
        index++
        continue
      }

      val closeParenIndex = input.indexOf(')', index)
      val formatContent = input.substring(index + 1, closeParenIndex)
      val parts = formatContent.split('x')
      val length = parts[0].toInt()
      val repeat = parts[1].toLong()

      // Get the section to decompress recursively
      val startPos = closeParenIndex + 1
      val endPos = startPos + length
      val section = input.substring(startPos, endPos)

      // Recursively calculate the decompressed length of this section
      val sectionLength = calculateDecompressedLength(section)
      total += sectionLength * repeat

      // Move index past the processed section
      index = endPos
    }

    return total
  }

}

fun main() {
  Day9().start()
}