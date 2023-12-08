import java.lang.RuntimeException

fun main() {
    val solver = Day08(readInput("Day08"))
    val solverPartTwo = Day08(readInput("Day08part2"))
    
    println("Part one: " + solver.taskOne().toString())
    println("Part two: " + solverPartTwo.taskTwo().toString())
}

class Day08(private val input: List<String>) {

    private val navigation = createNavigation()
    private val network: Map<String, Pair<String,String>> = createNetwork()

    fun taskOne(): Int {
        var indexNetwork = 0
        var steps = 0
        var key = "AAA"
        while (true) {
            val direction = navigation[indexNetwork % navigation.length]
            indexNetwork += 1

            if (direction == 'R') {
                key = network[key]?.second ?: throw RuntimeException("no key in network")
                steps++
            }
            else if(direction == 'L') {
                key = network[key]?.first ?: throw RuntimeException("no key in network")
                steps++
            }

            if (key == "ZZZ")
            {
                return steps
            }
        }
    }

    fun taskTwo(): Long {
        val nodes = network.filter { it.key.last() == 'A' }.keys.toMutableList()
        val listOfSteps = mutableListOf<Long>()
        for (node in nodes) {
            var indexNetwork = 0
            var steps = 0
            var key = node
            while (true) {
                val direction = navigation[indexNetwork % navigation.length]
                indexNetwork += 1

                if (direction == 'R') {
                    key = network[key]?.second ?: throw RuntimeException("no key in network")
                    steps++
                }
                else if(direction == 'L') {
                    key = network[key]?.first ?: throw RuntimeException("no key in network")
                    steps++
                }
                if (key.last() == 'Z')
                {
                    listOfSteps.add(steps.toLong())
                    break
                }
            }
        }
        return listOfSteps.lcm()
    }

    private fun createNavigation() = input[0]

    private fun createNetwork(): Map<String, Pair<String,String>> {
        return input.drop(2).associate {
            val (key, value) = it.split(" = ")
            val (left, right) = value.filter {char -> char != '(' && char != ')' }.split(", ")
            key to Pair(left, right)
        }
    }

    private fun  MutableList<Long>.lcm() = reduce { a, b -> lcm(a, b) }

    private fun lcm(first: Long, second: Long): Long = (first * second) / gcd(first, second)

    private fun gcd(first: Long, second: Long): Long = if (first == 0L) second else gcd(second % first, first)
}