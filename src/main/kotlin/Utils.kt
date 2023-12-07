import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String) = Path("src/main/data/$name").readLines()

fun dailyTemplate(fileName: String) {
    val content = """
        fun main() {
            val solver = ${fileName}(readInput("$fileName"))
            
            println("Part one: " + solver.taskOne().toString())
            println("Part two: " + solver.taskTwo().toString())
        }
        
        class ${fileName}(private val input: List<String>) {

            fun taskOne(): Int {
                return input.size 
            }

            fun taskTwo(): Int {
                return input.size 
            }
        }
    """.trimIndent()

    val codeFile = "src/main/kotlin/$fileName.kt"
    val dataFile = "src/main/data/$fileName"
    File(codeFile).writeText(content)
    File(dataFile).createNewFile()
}

fun main() {
    dailyTemplate("Day07")
}