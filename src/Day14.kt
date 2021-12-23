fun main() {
    fun part1(input: List<String>): Int {
        var polymerTemplate = input[0].chunked(1).toMutableList()
        val pairInsertionRules = input.dropWhile { it.contains("->").not() }

        val insertionRuleMap = mutableMapOf<String, String>()
        for (rule in pairInsertionRules) {
            insertionRuleMap += rule.take(2) to rule.last().toString()
        }

        repeat(10) {
            val valuesToInsert = polymerTemplate.zipWithNext { a, b ->
                insertionRuleMap[arrayListOf(
                    a,
                    b
                ).joinToString("")]
            }

            val result = polymerTemplate.zip(valuesToInsert).flatMap { listOf(it.first, it.second!!) }.toMutableList()
            result += polymerTemplate.last().toString()

            polymerTemplate.clear()
            polymerTemplate.addAll(result)
        }

        val temp = polymerTemplate.groupBy { it }.values

        return temp.maxOf { it.size } - temp.minOf { it.size }
    }

    // takes too long, also answer does not fit in an Int
    fun part2firstTry(input: List<String>): Int {
        var polymerTemplate = input[0].chunked(1).toMutableList()
        val pairInsertionRules = input.dropWhile { it.contains("->").not() }

        val insertionRuleMap = mutableMapOf<String, String>()
        for (rule in pairInsertionRules) {
            insertionRuleMap += rule.take(2) to rule.last().toString()
        }

        for (step in 1..40) {
            val valuesToInsert = polymerTemplate.zipWithNext { a, b ->
                insertionRuleMap[arrayListOf(
                    a,
                    b
                ).joinToString("")]
            }

            val result = polymerTemplate.zip(valuesToInsert).flatMap { listOf(it.first, it.second!!) }.toMutableList()
            result += polymerTemplate.last().toString()

            polymerTemplate.clear()
            polymerTemplate.addAll(result)
        }

        val temp = polymerTemplate.groupBy { it }.values

        return temp.maxOf { it.size } - temp.minOf { it.size }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
//    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}