import java.lang.RuntimeException

fun main() {
    fun part1(input: List<String>): Int {
        val calibrationValues = input.map { line -> line.filter { it.isDigit() } }.map { "${it.first()}${it.last()}" }
        return calibrationValues.sumOf { it.toInt() }
    }

    val numberMap = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )

    fun findDigit(line: String, revers: Boolean = false): String {
        val indices = if (revers) line.length - 1 downTo 0 else line.indices

        return indices
            .asSequence()
            .map { index ->
                when {
                    line[index].isDigit() -> line[index].toString()
                    else -> numberMap[line.substring(index)]
                }
            }
            .firstOrNull { it != null } ?: throw RuntimeException("There is no digits")
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { findDigit(it) + findDigit(it, true) }
            .sumOf { it.toInt() }
    }

    val input = readInput("Day01")
    println("Part one: ${part1(input)}")
    println("Part two: ${part2(input)}")
}
