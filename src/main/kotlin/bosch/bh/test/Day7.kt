import java.io.File

class Day7 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day7_1.txt").readLines()
    val result1 = part1(data1)
    println("result1 = $result1")
    check(result1 == 2) { "expected 2 but got $result1" }

    val data2 = File("src/main/resources/day7_2.txt").readLines()
    val result2 = part1(data2)
    println("result2 = $result2")

    val result3 = part2(data1)
    println("result3 = $result3")

    check(result3 == 3) { "expected 3 but got $result3" }

    val result4 = part2(data2)
    println("result4 = $result4")
  }

  private fun part1(lines: List<String>): Int {
    val addressSplit = lines.map { line ->
      val outsideBrackets = line.split(Regex("\\[[^\\]]*\\]"))
        .filter { it.isNotEmpty() }

      val insideBrackets = Regex("\\[([^\\]]*)\\]").findAll(line)
        .map { it.groupValues[1] }
        .toList()

      Pair(outsideBrackets, insideBrackets)
    }

    val count = addressSplit.count { (outside, inside) ->
      print("$outside and $inside->")
      val abbaOutside = outside.any { isAbba(it) }
      val abbaInside = inside.any { isAbba(it) }
      val matches = abbaOutside && !abbaInside
      println("matches = $matches ($abbaOutside, $abbaInside)")
      matches
    }

    return count
  }

  private fun isAbba(line: String): Boolean {
    for (i in 0..line.length - 4) {
      if (line[i] == line[i + 3] && line[i + 1] == line[i + 2] && line[i] != line[i + 1]) {
        return true
      }
    }
    return false
  }

  private fun part2(lines: List<String>): Int {
    val addressSplit = lines.map { line ->
      val outsideBrackets = line.split(Regex("\\[[^\\]]*\\]"))
        .filter { it.isNotEmpty() }

      val insideBrackets = Regex("\\[([^\\]]*)\\]").findAll(line)
        .map { it.groupValues[1] }
        .toList()

      Pair(outsideBrackets, insideBrackets)
    }

    val count = addressSplit.count { (outside, inside) ->
      val abas = mutableListOf<String>()

      outside.forEach { element ->
        abas.addAll(getAbas(element))
      }

      inside.any { element ->
        abas.any {
          var bab = "${it[1]}${it[0]}${it[1]}"
          element.contains(bab)
        }
      }
    }

    return count
  }

  private fun getAbas(line: String): List<String> {
    val abas = mutableListOf<String>()

    for (i in 0..line.length - 3) {
      if (line[i] == line[i + 2] && line[i] != line[i + 1]) {
        abas.add("${line[i]}${line[i+1]}${line[i]}")
      }
    }

    return abas
  }

}

fun main() {
  Day7().start()
}