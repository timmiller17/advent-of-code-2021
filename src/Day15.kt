import java.util.*

fun main() {
    /*

    Assuming lowest risk path will only have down and right moves, use algorithm at this site
    https://www.geeksforgeeks.org/print-all-possible-paths-from-top-left-to-bottom-right-of-a-mxn-matrix/
    to find all paths and then choose path of least risk.

    I doubt this works for this whole day's worth of problems, but it might get me the sample and,
    if I'm lucky, part1.

    UPDATE: Works for my extra small part0 and for the part 1 sample, but too slow for part1 real data.
     */

    fun part0(input: List<String>): Int {
        val cave = input.map { it.chunked(1).map { it.toInt() } }

        val paths = mutableListOf<List<Int>>()

        findAllPaths(cave, paths)

        return paths.minOf { it.sum() } - cave[0][0]
    }

    fun part1(input: List<String>): Int {
        val cave = input.map { it.chunked(1).map { it.toInt() } }

        val paths = mutableListOf<List<Int>>()

        findAllPaths(cave, paths)

        return paths.minOf { it.sum() } - cave[0][0]
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput0 = readInput("Day15_test0")
    val testInput = readInput("Day15_test")
    check(part0(testInput0) == 20)
    check(part1(testInput) == 40)
//    check(part2(testInput) == 5)

    val input = readInput("Day15")
//    println("part1 ${part1(input)}")
//    println("part2 ${part2(input)}")
}

data class Location(
    val path: List<Int>,
    val i: Int,
    val j: Int,
)


fun findAllPaths(cave: List<List<Int>>, paths: MutableList<List<Int>>) {
    val n = cave.size
    val m = cave[0].size

    var queue = LinkedList<Location>()

    queue.add(Location(listOf(cave[0][0]), 0, 0))

    while (queue.isNotEmpty()) {
        val location = queue.first
        queue.removeFirst()

        // if we reached the end
        if (location.i == n - 1 && location.j == m - 1) {
            val path = mutableListOf<Int>()
            for (x in location.path) {
                path += x
            }
            paths += path
        } else if (location.i == n - 1) {
            // if we are in the last row, we can only move right
            val temp = mutableListOf<Int>().apply { addAll(location.path) }
            temp.add(cave[location.i][location.j + 1])
            queue.add(Location(temp, location.i, location.j + 1))
        } else if (location.j == m - 1) {
            // if we are in the last column, we can only move down
            val temp = mutableListOf<Int>().apply { addAll(location.path) }
            temp.add(cave[location.i + 1][location.j])
            queue.add(Location(temp, location.i + 1, location.j))
        } else {
            // can move right and down
            val temp1 = mutableListOf<Int>().apply { addAll(location.path) }
            temp1.add(cave[location.i][location.j + 1])
            queue.add(Location(temp1, location.i, location.j + 1))

            val temp2 = mutableListOf<Int>().apply { addAll(location.path) }
            temp2.add(cave[location.i + 1][location.j])
            queue.add(Location(temp2, location.i + 1, location.j))
        }
    }
}