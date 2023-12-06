fun main() {
    val solver = Day06(readInput("Day06"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day06(private val input: List<String>) {

    private val time = input[0].substringAfter(": ").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    private val distances = input[1].substringAfter(": ").split(" ").filter { it.isNotBlank() }.map { it.toInt() }

    fun taskOne(): Int {
        val result = MutableList(time.size) {
            0
        }
        distances.mapIndexed { index, distance ->
            for (beginningSpeed in 0..time[index]) {
                val raceTime = time[index] - beginningSpeed
                if (raceTime * beginningSpeed > distance) {
                    result[index]++
                }
            }

        }
        return result.reduce{acc, next -> acc * next  }
    }

    fun taskTwo(): Long {
        var result = 0L
        val longDistance = input[1].substringAfter(": ").filter { it.isDigit() }.toLong()
        val longTime = input[0].substringAfter(": ").filter { it.isDigit() }.toLong()
        for (beginningSpeed in 0..longTime) {
                val raceTime = longTime - beginningSpeed
                if (raceTime * beginningSpeed > longDistance) {
                    result++
                }
            }
        return result
    }
}