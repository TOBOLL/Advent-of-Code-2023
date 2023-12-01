fun main() {
    val solver = Day01(readInput("Day01"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day01(private val input: List<String>) {
    fun taskOne(): Int {
        return input.sumOf { line ->
            line.first { it.isDigit() }.digitToInt() * 10 + line.last { it.isDigit() }.digitToInt()
        }
    }

    fun taskTwo(): Int {
        return input.sumOf { line ->
            line.findAnyOf(numberMap.keys).getValue() * 10 + line.findLastAnyOf(numberMap.keys).getValue()
        }
    }

    private fun Pair<Int, String>?.getValue() = numberMap.getValue(checkNotNull(this).second).toInt()

    private val numberMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )
}
