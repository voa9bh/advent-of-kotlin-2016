package bosch.bh.test

import java.io.File
import kotlin.math.absoluteValue

class Position(var x: Int, var y: Int) {

  constructor(position: Position) : this(position.x, position.y)

  fun move(direction: Direction, steps: Int) {
    when (direction) {
      Direction.EAST -> x += steps
      Direction.WEST -> x -= steps
      Direction.SOUTH -> y -= steps
      Direction.NORTH -> y += steps
    }
  }

  override fun equals(other: Any?): Boolean {
    if( this === other ) return true
    if( other !is Position ) return false
    return this.x == other.x && this.y == other.y
  }

  override fun toString(): String {
    return "Position(x=$x, y=$y)"
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
    check(result1 == 8) { "Expected 8 but got ${result1}" }

    val data2 = File("src/main/resources/day1_2.txt").readText().split(",").map { it.trim() }
    val result2 = part1(data2)
    println("Result: $result2")

    val result3 = part2(data1)
    println("Result: $result3")

    val result4 = part2(data2)
    println("Result: $result4")
  }

  fun part1(data1: List<String>): Int {
    var direction: Direction? = Direction.NORTH
    val position = Position(0, 0)

    data1.forEach { s ->
      direction = move(s, direction!!, position)
    }
    println("Part1: ${position.x}, ${position.y}")
    return position.x.absoluteValue + position.y.absoluteValue
  }

  private fun part2(data1: List<String>): Int {
    var direction: Direction? = Direction.NORTH
    val position = Position(0, 0)

    val positions = mutableListOf<Position>()

    for (s in data1) {
      direction = move(s, direction!!, position, positions)
      if(direction == null) {
        break
      }
    }
    println("Part2: ${position.x}, ${position.y}")
    return position.x.absoluteValue + position.y.absoluteValue
  }

  private fun move(s: String, direction: Direction, position: Position, positions: MutableList<Position>? = null): Direction? {
    var newDirection = direction

    when (s[0]) {
      'R' -> newDirection = newDirection.turnRight()
      'L' -> newDirection = newDirection.turnLeft()
    }
    val steps = s.substring(1).toInt()

    if(positions == null) {
      position.move(newDirection, steps)
    } else {
      repeat(steps) {
        position.move(newDirection, 1)
        if(positions.contains(position)) {
          println("found ${position.x}, ${position.y}")
          return null
        }
        positions += Position(position)
      }
    }

    return newDirection
  }
}

fun main() {
  Day1().start()
}

