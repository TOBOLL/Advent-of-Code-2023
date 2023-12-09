fun main() {
    val solver = Day09(readInput("Day09"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day09(input: List<String>) {

    fun taskOne(): Long {
        val results = mutableListOf<Long>()
        for (measurement in sensorHistory) {
            results.add(lastDigits(measurement).sum())
        }
        return results.sum()
    }

    fun taskTwo(): Long {
        val results = mutableListOf<Long>()
        for (measurement in sensorHistory) {
            results.add(lastDigits(measurement.reversed()).sum())
        }
        return results.sum()
    }

    private val sensorHistory = input.map {
        val numbers = it.split(" ")
        numbers.map { number -> number.toLong() }
    }

    private fun lastDigits(measurement: List<Long>): MutableList<Long> {
        val lastDigits = mutableListOf(measurement.last())
        var diff = measurement.zipWithNext { a, b -> b - a }.toMutableList()
        lastDigits.add(diff.last())

        while (!diff.all { it == diff.first() }) {
            diff = diff.zipWithNext { a, b -> b - a }.toMutableList()
            lastDigits.add(diff.last())
        }
        return lastDigits
    }

}