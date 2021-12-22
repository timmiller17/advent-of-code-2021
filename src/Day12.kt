fun main() {
    fun part1(input: List<String>): Int {
        val caveSegments = input.map { it.split("-") }.map { CaveSegment(start = it[0], end = it[1]) }

        val startSegments = caveSegments.filter { it.start == "start" }
        val possiblePaths = mutableSetOf<MutableList<String>>()

        for (segment in startSegments) {
            possiblePaths += mutableListOf(segment.start, segment.end)
        }

        var pathsUpdated = true
        val newPathsToAdd = mutableListOf<MutableList<String>>()
        val oldPathsToRemove = mutableListOf<MutableList<String>>()
        while (pathsUpdated) {
            pathsUpdated = false

            possiblePaths.addAll(newPathsToAdd)
            possiblePaths.removeAll(oldPathsToRemove)
            newPathsToAdd.clear()
            oldPathsToRemove.clear()
            for (path in possiblePaths.filter { (it.last() == "end").not() }) {
                val possibleNextSegments = caveSegments.filter { it.start == path.last() || it.end == path.last() }


                for (segment in possibleNextSegments) {
                    var nextPossibleStep = if (path.last() == segment.start) {
                        segment.end
                    } else {
                        segment.start
                    }
                    if (nextPossibleStep.first().isUpperCase() || path.contains(nextPossibleStep).not()) {
                        pathsUpdated = true
                        val newPath: MutableList<String> = mutableListOf<String>().apply { addAll(path) }
                        newPath += nextPossibleStep
                        newPathsToAdd += newPath
                        oldPathsToRemove += path
                    }
                }
            }

        }

        return possiblePaths.filter { it.last() == "end" }.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
//    check(part2(testInput) == 5)

    val input = readInput("Day12")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

data class CaveSegment(val start: String, val end: String)