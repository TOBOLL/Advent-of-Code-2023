import Day07.Figure.FIVE_OF_A_KIND
import Day07.Figure.FOUR_OF_A_KIND
import Day07.Figure.FULL_HOUSE
import Day07.Figure.HIGH_CARD
import Day07.Figure.ONE_PAIR
import Day07.Figure.THREE_OF_A_KIND
import Day07.Figure.TWO_PAIR

fun main() {
    val solver = Day07(readInput("Day07"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day07(private val input: List<String>) {

    fun taskOne(): Long {
        val hands = createHands()
        val sortedResults = hands.sorted()
        return sortedResults.mapIndexed { index, hand -> hand.bid * (sortedResults.size - index) }.sum()
    }

    fun taskTwo(): Long {
        val hands = createHandsWithJokers()
        val sortedResults = hands.sorted()
        sortedResults.forEach {
            if (it.cards.contains('J'))
            println("cards: ${it.cards} figure: ${it.bestFigure}")
        }
        return sortedResults.mapIndexed { index, hand -> hand.bid * (sortedResults.size - index) }.sum()
    }

    private fun createHands() = input.map {
        val (cards, bid) = it.split(" ")
        Hand(cards, bid.toLong(), checkBestFigure(cards))
    }

    private fun createHandsWithJokers() = input.map {
        val (cards, bid) = it.split(" ")
        Hand(cards, bid.toLong(), checkBestFigureWithJoker(cards))
    }

    private fun checkBestFigure(cards: String): Figure {
        val figure = cards.groupingBy { it }.eachCount()

        if (figure.maxBy { it.value }.value == 5) {
            return FIVE_OF_A_KIND
        }
        if (figure.maxBy { it.value }.value == 4) {
            return FOUR_OF_A_KIND
        }
        if (figure.containsValue(3) && figure.containsValue(2)) {
            return FULL_HOUSE
        }
        if (figure.maxBy { it.value }.value == 3) {
            return THREE_OF_A_KIND
        }
        if (figure.filter { it.value == 2 }.size == 2) {
            return TWO_PAIR
        }
        if (figure.maxBy { it.value }.value == 2) {
            return ONE_PAIR
        }
        return HIGH_CARD
    }

    private fun checkBestFigureWithJoker(cards: String): Figure {
        val numberOfJokers = cards.count { it == 'J' }
        if (numberOfJokers == 0) {
            return checkBestFigure(cards)
        } else if (numberOfJokers == 5) {
            return FIVE_OF_A_KIND
        }

        val figure = cards.filter { it != 'J' }.groupingBy { it }.eachCount()
        if (figure.maxBy { it.value }.value + numberOfJokers == 5) {
            return FIVE_OF_A_KIND
        }
        if (figure.maxBy { it.value }.value + numberOfJokers == 4 ) {
            return FOUR_OF_A_KIND
        }
        var tempNumberOfJokers = numberOfJokers
        if (figure.maxBy { it.value }.value + numberOfJokers == 3) {
            tempNumberOfJokers -= (3 - figure.maxBy { it.value }.value)
            if (figure.maxBy { it.value }.value + tempNumberOfJokers == 2) {
                return FULL_HOUSE
            }
            return THREE_OF_A_KIND
        }
        if (figure.filter { it.value == 2 }.size == 2) {
            return TWO_PAIR
        }
        if (figure.maxBy { it.value }.value + numberOfJokers == 2) {
            return ONE_PAIR
        }
        return HIGH_CARD
    }

    data class Hand(
        val cards: String,
        val bid: Long,
        val bestFigure: Figure = HIGH_CARD,
    ) : Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            if (this.bestFigure > other.bestFigure) {
                return 1
            }
            else if (this.bestFigure < other.bestFigure) {
                return -1
            }
            for (i in 0..4) {
                if (valuesOfCards[this.cards[i]]!! > valuesOfCards[other.cards[i]]!!) {
                    return -1
                }
                else if (valuesOfCards[this.cards[i]]!! < valuesOfCards[other.cards[i]]!!) {
                    return 1
                }
            }
            return 0
        }
        private val valuesOfCards = mapOf(
            '2' to 2,
            '3' to 3,
            '4' to 4,
            '5' to 5,
            '6' to 6,
            '7' to 7,
            '8' to 8,
            '9' to 9,
            'T' to 10,
            'J' to 0,
            'Q' to 12,
            'K' to 13,
            'A' to 14,
        )
    }


    enum class Figure(val value: Int) {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1),
    }
}