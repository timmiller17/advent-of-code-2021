import kotlin.math.min
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val linesToConsider = input.map { Line(it) }
            .filter {it.start.first == it.end.first || it.start.second == it.end.second }

        val allPointsOnLines = mutableListOf<Pair<Int, Int>>()

        for (line in linesToConsider) {
            //generate all points on the line and add them to allPointsOnLines
            if (line.start.first == line.end.first) {
                val range = min(line.start.second, line.end.second)..max(line.start.second, line.end.second)
                for (i in range) {
                    allPointsOnLines += Pair(line.start.first, i)
                }
            } else {
                val range = line.start.first.coerceAtMost(line.end.first)..line.start.first.coerceAtLeast(line.end.first)
                for (i in range) {
                    allPointsOnLines += Pair(i, line.start.second)
                }
            }
        }

        return allPointsOnLines.groupBy { it } .count { it.value.size > 1 }
    }

    fun part2(input: List<String>): Int {
        val linesToConsider = input.map { Line(it) }
        val allPointsOnLines = mutableListOf<Pair<Int, Int>>()

        for (line in linesToConsider) {
            //generate all points on the line and add them to allPointsOnLines
            if (line.start.first == line.end.first) {
                val range = min(line.start.second, line.end.second)..max(line.start.second, line.end.second)
                for (i in range) {
                    allPointsOnLines += Pair(line.start.first, i)
                }
            } else if (line.start.second == line.end.second){
                val range = line.start.first.coerceAtMost(line.end.first)..line.start.first.coerceAtLeast(line.end.first)
                for (i in range) {
                    allPointsOnLines += Pair(i, line.start.second)
                }
            } else {
                val xProgression: IntProgression = if (line.start.first < line.end.first) {
                    line.start.first..line.end.first
                } else {
                    line.start.first downTo line.end.first
                }
                val yProgression: IntProgression = if (line.start.second < line.end.second) {
                    line.start.second..line.end.second
                } else {
                    line.start.second downTo line.end.second
                }
                val xList = xProgression.toList()
                val yList = yProgression.toList()

                for (i in 0 until xList.size) {
                    allPointsOnLines += Pair(xList[i], yList[i])
                }
            }
        }

        return allPointsOnLines.groupBy { it } .count { it.value.size > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

data class Line(val rawLine: String) {
    lateinit var start: Pair<Int, Int>
    lateinit var end: Pair<Int, Int>

    init {
        val points = rawLine.split(" -> ")
        val startStrings = points[0].split(",")
        start = Pair(startStrings[0].toInt(), startStrings[1].toInt())
        val endStrings = points[1].split(",")
        end = Pair(endStrings[0].toInt(), endStrings[1].toInt())
    }
}