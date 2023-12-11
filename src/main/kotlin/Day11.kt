import kotlin.math.abs

fun main() {
    val solver = Day11(readInput("Day11"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day11(private val input: List<String>) {

    fun taskOne(): Int {
        val newMap = reBuildMap()
        val galaxies = mutableListOf<Pair<Int, Int>>()
        newMap.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    galaxies.add(Pair(x, y))
                }
            }
        }

        val distances = mutableListOf<Int>()
        for (element in galaxies) {
            galaxies.forEach {
                val dis = abs(element.first - it.first) + abs(element.second - it.second)
                distances.add(dis)
            }
        }

        return distances.sum() / 2
    }

    fun taskTwo(): Long {
        val points = getLongDistancePoints()

        val galaxies = mutableListOf<Pair<Int, Int>>()

        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    galaxies.add(Pair(x, y))
                }
            }
        }

        val distances = mutableListOf<Long>()
        for (element in galaxies) {
            galaxies.forEach { galaxy ->
                val xMultiplier = pointsBetween(points.rowIndexesList, element.first, galaxy.first)
                val yMultiplier = pointsBetween(points.columnIndexesList, element.second, galaxy.second)
                val dis =
                    abs(element.first - galaxy.first) + abs(element.second - galaxy.second) + (xMultiplier + yMultiplier) * 999999L
                distances.add(dis)
            }
        }

        return distances.sum() / 2
    }

    private fun pointsBetween(list: List<Int>, p1: Int, p2: Int): Int {
        var result = 0
        for (line in list) {
            if (line in (p1 + 1)..<p2) {
                result++
            }
            else if (line in (p2 + 1)..<p1) {
                result++
            }
        }
        return result
    }

    private fun getLongDistancePoints(): Distance {
        val columnIndexes = MutableList(input.first().length) { it }

        input.forEach { line ->
            line.forEachIndexed { i, char ->
                if (char != '.') {
                    columnIndexes.remove(i)
                }
            }
        }

        val rowIndexes = input.mapIndexedNotNull { index, line ->
            if (line.all { it == '.' }) {
                index
            } else {
                null
            }
        }
        return Distance(rowIndexes, columnIndexes)
    }

    private fun reBuildMap(): MutableList<String> {
        val distance = getLongDistancePoints()

        val newMap = input.map { line ->
            var newLine = ""
            line.forEachIndexed { index, c ->
                newLine += c
                if (distance.rowIndexesList.contains(index)) {
                    newLine += '.'
                }
            }
            newLine
        }.toMutableList()

        distance.columnIndexesList.forEachIndexed { index, int ->
            newMap.add(int + index + 1, ".".repeat(newMap.first().length))
        }

        return newMap
    }

    data class Distance(
        val columnIndexesList: List<Int>,
        val rowIndexesList: List<Int>
    )
}