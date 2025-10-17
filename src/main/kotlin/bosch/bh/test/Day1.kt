package bosch.bh.test

import java.io.File
import kotlin.math.absoluteValue

class Position(var x: Int, var y: Int) {
  fun move(direction: Direction, steps: Int) {
    when (direction) {
      Direction.EAST -> x += steps
      Direction.WEST -> x -= steps
      Direction.SOUTH -> y -= steps
      Direction.NORTH -> y += steps
    }
  }
}

enum class Direction {
  NORTH, EAST, SOUTH, WEST;

  fun turnLeft(): Direction {
    var index = entries.indexOf(this) - 1
    if (index < 0) {
      index = entries.lastIndex + (index + 1)
    }
    return entries[index]
  }

  fun turnRight(): Direction {
    var index = entries.indexOf(this) + 1
    if (index > entries.lastIndex) {
      index -= entries.size
    }
    return entries[index]
  }
}

class Day1 {
  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day1_1.txt").readText().split(",").map { it.trim() }
    val result1 = part1(data1)
    check(result1 == 8)

    val data2 = File("src/main/resources/day1_2.txt").readText().split(",").map { it.trim() }
    val result2 = part1(data2)
    println("Result: $result2")

    val result3 = part2(data1)
    println("Result: $result3")
  }

  fun part1(data1: List<String>): Int {
    var direction = Direction.NORTH
    val position = Position(0, 0)

    data1.forEach { s ->
      direction = move(s, direction, position)
    }
    println("Part1: ${position.x}, ${position.y}")
    return position.x.absoluteValue + position.y.absoluteValue
  }

  private fun part2(data1: List<String>): Int {
    var direction = Direction.NORTH
    val position = Position(0, 0)

    val positions = mutableListOf<Position>()

    for (s in data1) {
      direction = move(s, direction, position)
      if (positions.contains(position)) {
        break
      }

      positions.add(Position(position.x, position.y))
    }
    println("Part2: ${position.x}, ${position.y}")
    return position.x.absoluteValue + position.y.absoluteValue
  }

  private fun move(s: String, direction: Direction, position: Position): Direction {
    var newDirection = direction
    when (s[0]) {
      'R' -> newDirection = newDirection.turnRight()
      'L' -> newDirection = newDirection.turnLeft()
    }
    val steps = s.substring(1).toInt()
    position.move(newDirection, steps)
    return newDirection
  }
}

fun main() {
  Day1().start()
}