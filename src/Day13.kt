fun main() {
    fun part1(input: List<String>): Int {
        val points = input.filter { it.contains(",") }.map { it.split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }
        val foldInstructions = input.filter { it.contains("=") }
            .map {  it.split("=") }
            .map { FoldInstruction(it[0].last().toString(), it[1].toInt()) }

        val foldedPoints = mutableSetOf<Pair<Int, Int>>()
        for (point in points) {
            val direction = foldInstructions[0].direction
            val position = foldInstructions[0].position
            if (direction == "y") {
                if (point.second < position) {
                    foldedPoints += point
                } else {
                    val newY = position - (point.second - position)
                    foldedPoints += Pair(point.first, newY)
                }
            } else {
                if (point.first > position) {
                    foldedPoints += point
                } else {
                    val newX = position + (position - point.first)
                    foldedPoints += Pair(newX, point.second)
                }
            }
        }
        return foldedPoints.size
    }

    fun part2(input: List<String>): Int {
        val points = input.filter { it.contains(",") }
            .map { it.split(",") }
            .map { Pair(it[0].toInt(), it[1].toInt()) }
            .toMutableSet()
        val foldInstructions = input.filter { it.contains("=") }
            .map {  it.split("=") }
            .map { FoldInstruction(it[0].last().toString(), it[1].toInt()) }

        for (foldInstruction in foldInstructions) {
            val foldedPoints = mutableSetOf<Pair<Int, Int>>()
            for (point in points) {
                val direction = foldInstruction.direction
                val position = foldInstruction.position
                if (direction == "y") {
                    if (point.second < position) {
                        foldedPoints += point
                    } else {
                        val newY = position - (point.second - position)
                        foldedPoints += Pair(point.first, newY)
                    }
                } else {
                    if (point.first < position) {
                        foldedPoints += point
                    } else {
                        val newX = position - (point.first - position)
                        foldedPoints += Pair(newX, point.second)
                    }
                }
            }
            points.clear()
            points.addAll(foldedPoints)
            foldedPoints.clear()
        }

        val minX = points.minOf { it.first }
        val maxX = points.maxOf { it.first }
        val minY = points.minOf { it.second }
        val maxY = points.maxOf { it.second }
        println("\nmaxX:$maxX minX:$minX")
        println("maxY:$maxY minY:$minY\n")

        for (y in minY..maxY) {
            var line = ""
            for (x in minX..maxX) {
                if (points.contains(Pair(x, y))) {
                    line += "#"
                } else {
                    line += "."
                }
            }
            println(line)
        }

        return points.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    check(part2(testInput) == 16)

    val input = readInput("Day13")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

data class FoldInstruction(val direction: String, val position: Int)
