import kotlin.math.max
import kotlin.math.min

fun main() {
    val solver = Day05(readInput("Day05"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day05(private val input: List<String>) {

    private val seeds = createSeed()
    private val seedsPair = createSeedPairs()
    private val farm = createFarm()

    fun taskOne(): Long {
        val results = mutableListOf<Long>()

        seeds.forEach { seed ->
            var location = seed
            farm.forEach { fields ->
                var seedInRange = location
                fields.forEach {
                    if (location in it.source..<it.source + it.range) {
                        seedInRange = it.destination + (seedInRange - it.source)
                    }
                }
                location = seedInRange
            }
            results.add(location)
        }
        return results.minOf { it }
    }

    fun taskTwo(): Long {
        val results = seedsPair.toMutableList()
        farm.forEach { fields ->
            val queue = results.toMutableList()
            results.clear()
            while (queue.size > 0) {
                val seed = queue.first()
                queue.removeFirst()
                var found = false
                for (field in fields) {
                    val overLapLeft = max(seed.first, field.source)
                    val overLapRight = min(seed.second, field.source + field.range - 1)

                    if (overLapLeft < overLapRight) {
                        results.add(overLapLeft - field.source + field.destination to overLapRight - field.source + field.destination)
                        if (overLapLeft > seed.first) {
                            queue.add(seed.first to overLapLeft - 1)
                        }
                        if (seed.second > overLapRight) {
                            queue.add(overLapRight to seed.second)
                        }
                        found = true
                        break
                    }
                }
                if (!found) {
                    results.add(seed.first to seed.second)
                }
            }
        }
        println(results)
        results.sortBy { it.first }
        return results.minBy { it.first }.first
    }

    private fun createSeed() = input[0].substringAfter(":").split(' ').filter { it.isNotBlank() }.map { it.toLong() }

    private fun createSeedPairs() =
        input[0].substringAfter(":").split(' ').filter { it.isNotBlank() }.map { it.toLong() }
            .chunked(2) { (first, second) ->
                Pair(first, first + second - 1)
            }.toMutableList()

    private fun createFarm(): MutableList<MutableList<FieldMap>> {
        val result: MutableList<MutableList<FieldMap>> = mutableListOf()
        var fields = mutableListOf<FieldMap>()
        input.drop(2).forEach { line ->
            if (line.isNotBlank() && line.first().isDigit()) {
                val lineSplit = line.split(" ").map { it.toLong() }
                fields.add(FieldMap(lineSplit[1], lineSplit[0], lineSplit[2]))
            }
            if (line.isBlank()) {
                result.add(fields)
                fields = mutableListOf()
            }
        }
        result.add(fields)
        return result
    }

    data class FieldMap(
        val source: Long,
        val destination: Long,
        val range: Long
    )
}