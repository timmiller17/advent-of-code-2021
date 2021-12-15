fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("DayXX_test")
    check(part1(testInput) == 7)
//    check(part2(testInput) == 5)

    val input = readInput("DayXX")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}