fun main() {
    fun part1(input: List<String>): Int {
        val commands = input.map { it.split(" ") }
            .groupBy(keySelector = { it[0] }, valueTransform = { it[1].toInt() })

        val downSum = commands["down"]?.sum()!!
        val upSum = commands["up"]?.sum()!!
        val forwardSum = commands["forward"]?.sum()!!

        return (downSum - upSum) * forwardSum
    }

    fun part2(input: List<String>): Int {
        var position = 0
        var aim = 0
        var depth = 0

        input.map { it.split(" ") }
            .forEach { command ->
                when (command[0]) {
                    "down" -> aim += command[1].toInt()
                    "up" -> aim -= command[1].toInt()
                    "forward" -> {
                        val distance = command[1].toInt()
                        position += distance
                        depth += aim * distance
                    }
                }
            }
        return position * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

