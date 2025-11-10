package bosch.bh.test

import java.io.File

class Day10 {
  open class Bot(val id: Int, val chips: MutableList<Int> = mutableListOf()) {
    open fun addChip(value: Int) {
      chips.add(value)
      if(chips.size > 2) {
        throw IllegalStateException("Bot $id has more than 2 chips: $chips")
      }
    }
  }

  class Output(id: Int) : Bot(id) {
    override fun addChip(value: Int) {
      chips.add(value)
    }
  }

  open class Instruction()

  class ValueGoesToBot(val value: Int, val bot: Bot) : Instruction() {
    override fun toString(): String {
      return "value $value goes to bot ${bot.id}"
    }
  }

  class BotGivesTo(val bot: Bot, val lowToBot: Bot, val highToBot: Bot) : Instruction() {
    override fun toString(): String {
      return "bot ${bot.id} gives low to ${if (lowToBot is Output) "output" else "bot"} ${lowToBot.id} and high to ${if (highToBot is Output) "output" else "bot"} ${highToBot.id}"
    }
  }

  fun start() {
    println("Start ${System.getProperty("user.dir")}")
    val data1 = File("src/main/resources/day10_1.txt").readLines()
    val result1 = part1(data1, Pair(5,2))
    println("result1 = $result1")
    check(result1 == 2) { "expected 2 but got $result1" }

    val data2 = File("src/main/resources/day10_2.txt").readLines()
    val result2 = part1(data2, Pair(61,17))
    println("result2 = $result2")
  }

  private fun part1(lines: List<String>, endCriteria:Pair<Int,Int>): Int {
    val bots = mutableMapOf<Int, Bot>()
    val outputs = mutableListOf<Output>()
    val instructions = mutableListOf<Instruction>()

    val sortedEndCriteria = if(endCriteria.first < endCriteria.second) endCriteria else Pair(endCriteria.second, endCriteria.first)

    // Parse input
    lines.forEach { line ->
      val parts = line.split(" ")
      if (parts[0] == "value") {
        val value = parts[1].toInt()
        val botId = parts[5].toInt()
        val bot = bots.getOrPut(botId) { Bot(botId) }
        instructions.add(ValueGoesToBot(value, bot))
      } else if (parts[0] == "bot") {
        val botId = parts[1].toInt()
        val lowType = parts[5]
        val lowId = parts[6].toInt()
        val highType = parts[10]
        val highId = parts[11].toInt()

        val bot = bots.getOrPut(botId) { Bot(botId) }
        val lowBot = if (lowType == "bot") {
          bots.getOrPut(lowId) { Bot(lowId) }
        } else {
          val output = Output(lowId)
          outputs.add(output)
          output
        }
        val highBot = if (highType == "bot") {
          bots.getOrPut(highId) { Bot(highId) }
        } else {
          val output = Output(highId)
          outputs.add(output)
          output
        }
        instructions.add(BotGivesTo(bot, lowBot, highBot))
      }
    }

    println(instructions.joinToString("\n"))

    for(instruction in instructions) {
      if(instruction is ValueGoesToBot) {
        instruction.bot.chips.add(instruction.value)
      }
    }

    repeat(10_000_000) {
      for(bot in bots) {
        println("Bot ${bot.key} has chips ${bot.value.chips}")
      }

      if(checkEndCriteria(bots, sortedEndCriteria) != -1) {
        println("End criteria met.")
        return checkEndCriteria(bots, sortedEndCriteria)
      }

      for(instruction in instructions) {
        if(instruction is BotGivesTo) {
          val bot = instruction.bot
          if(bot.chips.size == 2) {
            val low = bot.chips.min()
            val high = bot.chips.max()
            instruction.lowToBot.addChip(low)
            instruction.highToBot.addChip(high)
            bot.chips.clear()

            break
          }
        }
      }
    }

    return -1
  }

  private fun checkEndCriteria(
    bots: MutableMap<Int, Bot>,
    endCriteria: Pair<Int, Int>
  ): Int {
    for (bot in bots.values) {
      if (bot.chips.size == 2) {
        val low = bot.chips.minOrNull()!!
        val high = bot.chips.maxOrNull()!!
        if (low == endCriteria.first && high == endCriteria.second) {
          println("Bot ${bot.id} is comparing $low and $high")
          return bot.id
        }
      }
    }
    return -1
  }

}

fun main() {
  Day10().start()
}