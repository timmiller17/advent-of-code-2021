fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        for (i in 1..input.size - 1) {
            val a = input[i].toInt()
            val b = input[i - 1].toInt()
            if (a > b) {
                count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (i in 1..input.size - 3) {
            val slidingSum1 = input[i-1].toInt() + input[i].toInt() + input[i + 1].toInt()
            val slidingSum2 = input[i].toInt() + input[i + 1].toInt() + input[i + 2].toInt()

            if (slidingSum2 > slidingSum1) {
                count++
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

