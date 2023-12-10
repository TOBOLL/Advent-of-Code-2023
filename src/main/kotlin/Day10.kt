fun main() {
    val solver = Day10(readInput("Day10"))

    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solver.taskTwo().toString())
}

class Day10(private val input: List<String>) {

    fun taskOne(): Int {
        return getPoints().size/2
    }

    fun taskTwo(): Int {
        val pointsOfLoop = getPoints()
        var counter = 0
        for (y in input.indices){
            for (x in 0..<input.first().length) {
                if (!pointsOfLoop.contains(Pair(y,x)) && calculateIsItInSide(y,x,pointsOfLoop)) {
                    counter++
                }
            }
        }
        return counter
    }

    private fun getPoints():MutableList<Pair<Int,Int>>  {
        val sizeColumn = input.size
        val sizeRow = input.first().length

        val (startX, startY) = findCharacter('S').first()
        val toCheck = mutableListOf(Pair(startY, startX))
        val alreadyCheck = mutableListOf(Pair(startY, startX))

        while (toCheck.isNotEmpty()) {
            val (y, x) = toCheck.popLast()
            val currentCharacter = input[y][x]

            if(y > 0 && currentCharacter in NORTH && input[y-1][x] in SOUTH && !alreadyCheck.contains(Pair(y-1, x))) {
                toCheck.add(Pair(y-1, x))
                alreadyCheck.add(Pair(y-1, x))
            }
            if(y < sizeColumn - 1 && currentCharacter in SOUTH && input[y+1][x] in NORTH && !alreadyCheck.contains(Pair(y+1, x))) {
                toCheck.add(Pair(y+1, x))
                alreadyCheck.add(Pair(y+1, x))
            }

            if(x < sizeRow - 1 && currentCharacter in EAST && input[y][x+1] in WEST && !alreadyCheck.contains(Pair(y, x+1))) {
                toCheck.add(Pair(y, x+1))
                alreadyCheck.add(Pair(y, x+1))
            }
            if(x > 0 && currentCharacter in WEST && input[y][x-1] in EAST && !alreadyCheck.contains(Pair(y, x-1))) {
                toCheck.add(Pair(y, x-1))
                alreadyCheck.add(Pair(y, x-1))
            }
        }
        return alreadyCheck
    }
    private fun findCharacter(char: Char): List<Pair<Int, Int>> {
        return input.flatMapIndexed { y, string ->
            string.indexOf(char).let { x ->
                if (x != -1) listOf(x to y)
                else emptyList()
            }
        }
    }

    private fun calculateIsItInSide(y: Int, x: Int, points: List<Pair<Int, Int>>): Boolean {
        val number = points.count { it.first == y && it.second < x && input[it.first][it.second] in INTERSECTIONS_CHARACTERS }
        return number % 2 != 0
    }

    private fun MutableList<Pair<Int, Int>>.popLast(): Pair<Int, Int> {
        val value = this.last()
        this.removeLast()
        return value
    }

    private val INTERSECTIONS_CHARACTERS = "|7F"
    private val WEST = "-J7S"
    private val SOUTH = "|7FS"
    private val NORTH = "|LJS"
    private val EAST = "-LFS"
}