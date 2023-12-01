import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String) = Path("src/main/data/$name").readLines()

fun dailyTemplate(fileName: String) {
    val content = """
        fun main() {
            fun part1(input: List<String>): Int {
                return input.size
            }

            fun part2(input: List<String>): Int {
                return input.size
            }

            val testInput = readInput("$fileName")
            println("Part one: ")
            println("Part two: ")
        }
    """.trimIndent()

    val codeFile = "src/main/kotlin/$fileName.kt"
    val dataFile = "src/main/data/$fileName"
    File(codeFile).writeText(content)
    File(dataFile).createNewFile()
}

fun main() {
    dailyTemplate("Day02")
}