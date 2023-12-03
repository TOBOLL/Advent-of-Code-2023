import Day02.Colors.BLUE
import Day02.Colors.GREEN
import Day02.Colors.RED

fun main() {
    val solver = Day02(readInput("Day02"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day02(private val input: List<String>) {

    fun taskOne(): Int {
        val games = input.map { creatGame(it) }
        val maxBlue = 14
        val maxRed = 12
        val maxGreen = 13
        return games.filter { it.rounds.all { round -> round.blue <= maxBlue && round.red <= maxRed && round.green <= maxGreen } }
            .sumOf { it.id }
    }

    fun taskTwo(): Int {
        val games = input.map { creatGame(it) }
        return games.map { game ->
            val necessaryRed = game.rounds.maxBy { it.red }.red
            val necessaryBlue = game.rounds.maxBy { it.blue }.blue
            val necessaryGreen = game.rounds.maxBy { it.green }.green

            necessaryRed * necessaryBlue * necessaryGreen
        }.sumOf { it }
    }

    data class Game(
        val id: Int,
        val rounds: List<Round>
    )

    private fun creatGame(line: String): Game {
        val splitLine = line.split(": ")
        val gameId = splitLine[0].filter { it.isDigit() }.toInt()
        val rounds = splitLine[1].split("; ").map {
            var red = 0
            var green = 0
            var blue = 0
            it.split(", ").map { colorAndNumber ->
                val (number, color) = colorAndNumber.split(" ")
                when (color) {
                    RED.color -> red = number.toInt()
                    BLUE.color -> blue = number.toInt()
                    GREEN.color -> green = number.toInt()
                }
            }
            Round(green, red, blue)
        }
        return Game(gameId, rounds)
    }

    data class Round(
        val green: Int,
        val red: Int,
        val blue: Int
    )

    enum class Colors(val color: String) {
        RED("red"),
        BLUE("blue"),
        GREEN("green")
    }
}