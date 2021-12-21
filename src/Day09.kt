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
        val heightMap = input.map { it.chunked(1).map { it.toInt() } }
        val basinSizes = mutableListOf<Int>()
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
                    val basinPoints = mutableSetOf<Pair<Int, Int>>()
                    val totalBasinPoints = mutableSetOf<Pair<Int, Int>>(Pair(i, j))
                    var basinPointHeight = heightMap[i][j]
                    if (leftPositionInBasin(i, j, basinPointHeight, heightMap)) {
                        basinPoints += Pair(i, j - 1)
                    }
                    if (rightPositionInBasin(i, j, basinPointHeight, heightMap)) {
                        basinPoints += Pair(i, j + 1)
                    }
                    if (topPositionInBasin(i, j, basinPointHeight, heightMap)) {
                        basinPoints += Pair(i - 1, j)
                    }
                    if (bottomPositionInBasin(i, j, basinPointHeight, heightMap)) {
                        basinPoints += Pair(i + 1, j)
                    }
                    val workingBasinPoints = mutableSetOf<Pair<Int, Int>>()
                    workingBasinPoints.addAll(basinPoints)
                    totalBasinPoints.addAll(basinPoints)
                    basinPoints.clear()

                    while (workingBasinPoints.isNotEmpty()) {
                        val point = workingBasinPoints.first()
                        workingBasinPoints.remove(point)
                        basinPointHeight = heightMap[point.first][point.second]
                        if (leftPositionInBasin(point.first, point.second, basinPointHeight, heightMap)) {
                            basinPoints += Pair(point.first, point.second - 1)
                        }
                        if (rightPositionInBasin(point.first, point.second, basinPointHeight, heightMap)) {
                            basinPoints += Pair(point.first, point.second + 1)
                        }
                        if (topPositionInBasin(point.first, point.second, basinPointHeight, heightMap)) {
                            basinPoints += Pair(point.first - 1, point.second)
                        }
                        if (bottomPositionInBasin(point.first, point.second, basinPointHeight, heightMap)) {
                            basinPoints += Pair(point.first + 1, point.second)
                        }
                        workingBasinPoints.addAll(basinPoints)
                        totalBasinPoints.addAll(basinPoints)
                        basinPoints.clear()
                    }

                    basinSizes += totalBasinPoints.size
                }

            }
        }

        return basinSizes.sorted().takeLast(3).reduce(Int::times)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

fun leftPositionInBasin(i: Int, j: Int, basinPointHeight: Int, heightMap: List<List<Int>>): Boolean {
    if (j == 0) {
        return false
    }
    return heightMap[i][j -  1].inBasin(basinPointHeight)
}

fun rightPositionInBasin(i: Int, j: Int, basinPointHeight: Int, heightMap: List<List<Int>>): Boolean {
    if (j == heightMap[i].indices.last) {
        return false
    }
    return heightMap[i][j + 1].inBasin(basinPointHeight)
}

fun topPositionInBasin(i: Int, j: Int, basinPointHeight: Int, heightMap: List<List<Int>>): Boolean {
    if (i == 0) {
        return false
    }
    return heightMap[i - 1][j].inBasin(basinPointHeight)
}

fun bottomPositionInBasin(i: Int, j: Int, basinPointHeight: Int, heightMap: List<List<Int>>): Boolean {
    if (i == heightMap.indices.last) {
        return false
    }
    return heightMap[i + 1][j].inBasin(basinPointHeight)
}

fun Int.inBasin(basinPointHeight: Int): Boolean {
    return this in (basinPointHeight + 1)..8
}
