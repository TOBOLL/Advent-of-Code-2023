fun main() {
    val solver = Day03(readInput("Day03"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day03(private val input: List<String>) {

    private val values = createRows()
    private val mapAroundSpecialCharacter = listOf(
        Pair(1, 0),
        Pair(-1, 0),
        Pair(1, 1),
        Pair(-1, 1),
        Pair(0, 1),
        Pair(0, -1),
        Pair(1, -1),
        Pair(-1, -1)
    )

    fun taskOne(): Int {
        var result = 0
        input.forEachIndexed { row, line ->
            line.forEachIndexed { column, character ->
                if (!character.isDigit() && character != '.') {
                    mapAroundSpecialCharacter.forEach {
                        values[row + it.second].index.mapIndexed { i, range ->
                            if (column + it.first in range) {
                                result += values[row + it.second].number[i].toInt()
                                values[row + it.second].number[i] = "0"
                            }
                        }
                    }
                }
            }
        }
        return result
    }

    fun taskTwo(): Int {
        var result = 0
        input.forEachIndexed { row, line ->
            line.forEachIndexed { column, character ->
                if (character == '*') {
                    var valuesAround = mutableListOf<Int>()
                    mapAroundSpecialCharacter.forEach {
                        values[row + it.second].index.forEachIndexed { i, range ->
                            if (column + it.first in range) {
                                valuesAround.add(values[row + it.second].number[i].toInt())
                                values[row + it.second].number[i] = "-1"
                            }
                        }
                    }
                    valuesAround = valuesAround.filter { it != -1 }.toMutableList()
                    if (valuesAround.size == 2)
                        result += valuesAround[0] * valuesAround[1]
                }
            }
        }
        return result
    }

    data class Row(
        val index: List<IntRange>,
        val number: MutableList<String>
    )

    fun createRows(): List<Row> {
        val result = mutableListOf<Row>()
        for (line in input) {
            val numbers = mutableListOf<String>()
            val numbersIndexes = mutableListOf<IntRange>()
            var indexOfNumbers = 0
            var preaviusIsDigit = false
            line.forEachIndexed { index, character ->
                if (character.isDigit()) {
                    if (!preaviusIsDigit) {
                        numbers.add(character.toString())
                        numbersIndexes.add(IntRange(index, index))
                    } else {
                        numbers[indexOfNumbers] = numbers[indexOfNumbers] + character
                        numbersIndexes[indexOfNumbers] =
                            IntRange(numbersIndexes[indexOfNumbers].first, numbersIndexes[indexOfNumbers].last + 1)
                    }
                    preaviusIsDigit = true
                } else {
                    if (preaviusIsDigit && numbers.isNotEmpty()) {
                        indexOfNumbers++
                    }
                    preaviusIsDigit = false
                }
            }
            result.add(Row(numbersIndexes, numbers))
        }
        return result
    }
}