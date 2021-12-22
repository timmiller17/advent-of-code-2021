fun main() {
    fun part1(input: List<String>): Int {
        val octopi = input.map { it.chunked(1) .map { it.toInt() }.toMutableList() }
        var flashes = 0

         for (step in 1..100) {
             val flashedOctopi = mutableSetOf<Pair<Int, Int>>()
             // initial energy level increase
             for (i in 0..9) {
                 for (j in 0..9) {
                     val energyLevel = ++octopi[i][j]
                     if (energyLevel == 10) {
                        flashedOctopi += Pair(i, j)
                    }
                }
             }
             // energy level increase due to flashes
             while (flashedOctopi.isNotEmpty()) {
                 val newlyFlashedOctopi = mutableSetOf<Pair<Int, Int>>()
                 for (octopus in flashedOctopi) {
                     newlyFlashedOctopi.addAll(addEnergyToNeighbors(octopus, octopi))
                 }
                 flashedOctopi.clear()
                 flashedOctopi.addAll(newlyFlashedOctopi)
             }

             // count flashes and reset flashed octopi to zero energy
             for (i in 0..9) {
                 for (j in 0..9) {
                     val energyLevel = octopi[i][j]
                     if (energyLevel > 9) {
                         octopi[i][j] = 0
                         flashes++
                     }
                 }
             }
        }

        return flashes
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
//    check(part2(testInput) == 5)

    val input = readInput("Day11")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

fun addEnergyToNeighbors(octopus: Pair<Int, Int>, octopi: List<MutableList<Int>>): MutableSet<Pair<Int, Int>> {
    val (i, j) = octopus
    val newlyFlashedDueToNeighbor = mutableSetOf<Pair<Int, Int>>()
    // has neighbors at every possible spot
    if (i in 1..8 && j in 1..8) {
        // top row
        if (++octopi[i - 1][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j - 1)
        }
        if (++octopi[i - 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j)
        }
        if (++octopi[i - 1][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j + 1)
        }
        // middle row (skipping center)
        if (++octopi[i][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j - 1)
        }
        if (++octopi[i][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j + 1)
        }
        // bottom row
        if (++octopi[i + 1][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j - 1)
        }
        if (++octopi[i + 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j)
        }
        if (++octopi[i + 1][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j + 1)
        }
    } else if (i == 0 && j in 1..8) { // top row, not on sides
        // middle row (skipping center)
        if (++octopi[i][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j - 1)
        }
        if (++octopi[i][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j + 1)
        }
        // bottom row
        if (++octopi[i + 1][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j - 1)
        }
        if (++octopi[i + 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j)
        }
        if (++octopi[i + 1][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j + 1)
        }
    } else if (i == 9 && j in 1..8) { // bottom row, not on sides
        // top row
        if (++octopi[i - 1][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j - 1)
        }
        if (++octopi[i - 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j)
        }
        if (++octopi[i - 1][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j + 1)
        }
        // middle row (skipping center)
        if (++octopi[i][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j - 1)
        }
        if (++octopi[i][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j + 1)
        }
    } else if (j == 0 && i in 1..8) { // left edge, not on top or bottom
        //top row
        if (++octopi[i - 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j)
        }
        if (++octopi[i - 1][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j + 1)
        }
        // middle row
        if (++octopi[i][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j + 1)
        }
        // bottom row
        if (++octopi[i + 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j)
        }
        if (++octopi[i + 1][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j + 1)
        }
    } else if (j == 9 && i in 1..8) { // right edge, not on top or bottom
        // top row
        if (++octopi[i - 1][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j - 1)
        }
        if (++octopi[i - 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j)
        }
        // middle row
        if (++octopi[i][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j - 1)
        }
        // bottom row
        if (++octopi[i + 1][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j - 1)
        }
        if (++octopi[i + 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j)
        }
    } else if (i == 0 && j == 0) { // top left corner
        // middle row
        if (++octopi[i][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j + 1)
        }
        // bottom row
        if (++octopi[i + 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j)
        }
        if (++octopi[i + 1][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j + 1)
        }
    } else if (i == 0 && j == 9) { // top right corner
        // middle row
        if (++octopi[i][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j - 1)
        }
        // bottom row
        if (++octopi[i + 1][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j - 1)
        }
        if (++octopi[i + 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i + 1, j)
        }
    } else if (i == 9 && j == 0) { // bottom left corner
        // top row
        if (++octopi[i - 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j)
        }
        if (++octopi[i - 1][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j + 1)
        }
        // middle row
        if (++octopi[i][j + 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j + 1)
        }
    } else if (i == 9 && j == 9) {
        // top row
        if (++octopi[i - 1][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j - 1)
        }
        if (++octopi[i - 1][j] == 10) {
            newlyFlashedDueToNeighbor += Pair(i - 1, j)
        }
        // middle row
        if (++octopi[i][j - 1] == 10) {
            newlyFlashedDueToNeighbor += Pair(i, j - 1)
        }
    }
    return newlyFlashedDueToNeighbor
}

