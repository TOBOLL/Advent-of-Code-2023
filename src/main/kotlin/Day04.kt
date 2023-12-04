import kotlin.math.pow

fun main() {
    val solver = Day04(readInput("Day04"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day04(private val input: List<String>) {

    private val cards = createCard()
    fun taskOne(): Int {
        return cards.map { it.winningNumbers.intersect(it.obtainedNumbers).count() - 1 }
            .mapNotNull {
                if (it >= 0)
                    2.0.pow(it)
                else null
            }
            .sumOf { it.toInt() }
    }

    fun taskTwo(): Int {
        val scratchcards = MutableList(cards.size) { 1 }
        cards.forEachIndexed { index, card ->
            card.winningNumbers.intersect(card.obtainedNumbers).forEachIndexed { increment, _ ->
                scratchcards[increment + index + 1] += scratchcards[index]
            }
        }
        return scratchcards.sumOf { it }
    }

    data class Card(
        val winningNumbers: Set<String>,
        val obtainedNumbers: Set<String>
    )

    private fun createCard(): List<Card> {
        return input.map {
            it.replace("  ", " ").substringAfter(": ")
        }.map {
            it.split(" | ").let { (winningNumbers, obtainedNumbers) ->
                Card(winningNumbers.split(" ").toSet(), obtainedNumbers.split(" ").toSet())
            }
        }
    }
}