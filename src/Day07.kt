import kotlin.math.abs
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val initialPositions = input[0].split(",").map { it.toInt() }.sorted()

        var distances = mutableListOf<Int>()

        for (i in initialPositions.first()..initialPositions.last()) {
            distances += getTotalDistanceFromPosition(i, initialPositions)
        }

        return distances.minOf { it }
    }

    fun part2(input: List<String>): Int {
        val initialPositions = input[0].split(",").map { it.toInt() }.sorted()

        var distances = mutableListOf<Int>()

        for (i in initialPositions.first()..initialPositions.last()) {
            distances += getTotalAdditiveDistanceFromPosition(i, initialPositions)
        }

        return distances.minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

fun getTotalDistanceFromPosition(position: Int, initialPositions: List<Int>): Int {
    var totalDistance = 0
    for (i in initialPositions.indices) {
        totalDistance += abs(position - initialPositions[i])
    }
    return totalDistance
}

fun getTotalAdditiveDistanceFromPosition(position: Int, initialPositions: List<Int>): Int {
    var totalDistance = 0
    for (i in initialPositions.indices) {
        val distance = abs(position - initialPositions[i])
        totalDistance += distance * (distance + 1) / 2
    }
    return totalDistance
}