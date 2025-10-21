import java.io.File
import kotlin.repeat

class Grid(val width: Int, val height: Int) {
  val grid = Array(height) { Array(width) { '.' } }

  override fun toString(): String {
    val sb = StringBuilder()
    grid.forEachIndexed { y, row ->
      row.forEachIndexed { x, ch ->
        sb.append(ch)
      }
      sb.append('\n')
    }
    return sb.toString()
  }

  fun set(x: Int, y: Int, ch: Char = '#') {
    grid[y][x] = ch
  }

  fun rotateColumn(x: Int, shift: Int) {
    val temp = Array(height) { curY -> grid[curY][x] }
    repeat(height) { curY ->
      val newPos = (curY + shift) % height
      grid[newPos][x] = temp[curY]
    }
  }

  fun rotateRow(y: Int, shift: Int) {
    val temp = Array(width) { curX -> grid[y][curX] }
    repeat(width) { curX ->
      val newPos = (curX + shift) % width
      grid[y][newPos] = temp[curX]
    }
  }
}

class Day8 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day8_1.txt").readLines()
    val result1 = part1(7, 3, data1)
    println("result1 = $result1")
    check(result1 == 6) { "expected 6 but got $result1" }

    val data2 = File("src/main/resources/day8_2.txt").readLines()
    val result2 = part1(50,6,data2)
    println("result2 = $result2")
//
//    val result3 = part2(data1)
//    println("result3 = $result3")
//
//    check(result3 == 3) { "expected 3 but got $result3" }
//
//    val result4 = part2(data2)
//    println("result4 = $result4")
  }

  private fun part1(width: Int, height: Int, lines: List<String>): Int {
    val grid = Grid(width, height)
    lines.forEach { line ->
      if (line.startsWith("rect")) {
        val coordinates = line.split(' ')[1]
        val (w, h) = coordinates.split("x").map(String::toInt)
        repeat(h) { y ->
          repeat(w) { x ->
            grid.set(x, y)
          }
        }
      } else if (line.startsWith("rotate")) {
        val parts = line.split(' ')
        if (parts[1] == "column") {
          val x = parts[2].substringAfter('=').toInt()
          val shift = parts[4].toInt()
          grid.rotateColumn(x, shift)
        } else if (parts[1] == "row") {
          val y = parts[2].substringAfter('=').toInt()
          val shift = parts[4].toInt()
          grid.rotateRow( y, shift)
        }
      }
      println("$grid\n")
    }

    val count = grid.toString().count { it == '#' }

    return count
  }

  private fun part2(lines: List<String>): Int {
    return 5
  }

}

fun main() {
  Day8().start()
}