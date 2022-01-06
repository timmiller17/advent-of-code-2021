import java.util.*

fun main() {
    /*

    For Part 0:
    Assuming lowest risk path will only have down and right moves, use algorithm at this site
    https://www.geeksforgeeks.org/print-all-possible-paths-from-top-left-to-bottom-right-of-a-mxn-matrix/
    to find all paths and then choose path of least risk.

    I doubt this works for this whole day's worth of problems, but it might get me the sample and,
    if I'm lucky, part1.

    UPDATE: Works for my extra small part0 and for the part 1 sample, but too slow for part1 real data.
    
    Next Update: Implemented Dijkstra algorithm and now working for part1!!!! Thanks Wikipedia!
    https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

     */

    fun part0(input: List<String>): Int {
        val cave = input.map { it.chunked(1).map { it.toInt() } }

        val paths = mutableListOf<List<Int>>()

        findAllPaths(cave, paths)

        return paths.minOf { it.sum() } - cave[0][0]
    }

    fun part1(input: List<String>): Int {
        val cave = input.map { it.chunked(1).map { it.toInt() }.toMutableList() }.toMutableList()

        val distances = mutableListOf<MutableList<Int>>()
        val previousNodes = mutableListOf<MutableList<Pair<Int, Int>?>>()

        for (list in cave) {
            distances += mutableListOf(list.map { Int.MAX_VALUE }.toMutableList())
            previousNodes += mutableListOf(list.map { null }.toMutableList())
        }
        distances[0][0] = 0

        val unvisitedSet = mutableSetOf<Node>()

        for (i in cave.indices) {
            for (j in cave[0].indices) {
                unvisitedSet += if (i == 0 && j == 0) {
                    Node(Pair(i,j), 0)
                } else {
                    Node(Pair(i,j))
                }
            }
        }

        while (unvisitedSet.isNotEmpty()) {
            val currentNode = unvisitedSet.minByOrNull { it.distance }!!
            unvisitedSet.remove(currentNode)

            val location = currentNode.location

            val neighbors = getNeighbors(location, cave.size, cave[0].size)  // get potential neighbors
            neighbors.removeAll { unvisitedSet.map { node -> node.location }.contains(it).not() } // remove neighbors already visited

            for (neighbor in neighbors) {
                val distance = distances[location.first][location.second] + getDistance(location, neighbor, cave)

                if (distance < distances[neighbor.first][neighbor.second]) {
                    distances[neighbor.first][neighbor.second] = distance
                    previousNodes[neighbor.first][neighbor.second] = location
                }
            }
        }

        val lowestRiskPath = mutableListOf(Pair(cave.size - 1, cave[0].size - 1))
        var previousNode = previousNodes[cave.size - 1][cave[0].size - 1]
        while (previousNode != Pair(0, 0)) {
            lowestRiskPath += previousNode!!
            previousNode = previousNodes[previousNode!!.first][previousNode.second]
        }

        return lowestRiskPath.sumOf { cave[it.first][it.second] }
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
    println("part1 ${part1(input)}")
//    println("part2 ${part2(input)}")
}

data class Node(
    val location: Pair<Int, Int>,
    val distance:Int = Int.MAX_VALUE,
)

fun getDistance(location: Pair<Int, Int>, neighbor: Pair<Int, Int>, cave: List<List<Int>>): Int {
    return cave[neighbor.first][neighbor.second]  // Chiton risk is equivalent to distance in adapting Dijkstra's algorithm to this problem space
}

fun getNeighbors(location: Pair<Int, Int>, caveRows: Int, caveColumns: Int): MutableList<Pair<Int, Int>> {
    val neighbors = mutableListOf<Pair<Int, Int>>()

    if (location.hasNorthNeighbor()) {
        neighbors += Pair(location.first - 1, location.second)
    }
    if (location.hasWestNeighbor()) {
        neighbors += Pair(location.first, location.second - 1)
    }
    if (location.hasSouthNeighbor(caveRows)) {
        neighbors += Pair(location.first + 1, location.second)
    }
    if (location.hasEastNeighbor(caveColumns)) {
        neighbors += Pair(location.first, location.second + 1)
    }

    return neighbors
}

fun Pair<Int, Int>.hasNorthNeighbor(): Boolean {
    return this.first > 0
}

fun Pair<Int, Int>.hasWestNeighbor(): Boolean {
    return this.second > 0
}

fun Pair<Int, Int>.hasSouthNeighbor(caveRows: Int): Boolean {
    return this.first < caveRows
}

fun Pair<Int, Int>.hasEastNeighbor(caveColumns: Int): Boolean {
    return this.second < caveColumns
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