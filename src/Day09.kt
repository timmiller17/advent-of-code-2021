fun main() {
    fun part1(input: List<String>): Int {
        val heightMap = input.map { it.chunked(1).map { it.toInt() } }
        val lowestPoints = mutableListOf<Int>()
        for (i in heightMap.indices) {
            for (j in heightMap[i].indices) {
                val localPoints = mutableListOf<Int>()
                if (j == 0) {
                    localPoints += heightMap[i][j + 1]
                } else if (j == heightMap[i].indices.last) {
                    localPoints += heightMap[i][j - 1]
                } else {
                    localPoints += heightMap[i][j - 1]
                    localPoints += heightMap[i][j + 1]
                }
                if (i == 0) {
                    localPoints += heightMap[i + 1][j]
                } else if (i == heightMap.indices.last) {
                    localPoints += heightMap[i - 1][j]
                } else {
                    localPoints += heightMap[i - 1][j]
                    localPoints += heightMap[i + 1][j]
                }
                if (heightMap[i][j] < localPoints.minOf { it }) {
                    lowestPoints += heightMap[i][j]
                }
            }
        }

        return lowestPoints.sumOf { it + 1 }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
//    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}
