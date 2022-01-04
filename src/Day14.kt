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

    fun part2(input: List<String>): Long {
        var polymerTemplate = input[0].chunked(1).toMutableList()
        val pairInsertionRules = input.dropWhile { it.contains("->").not() }

        val insertionRuleMap = mutableMapOf<String, String>()
        for (rule in pairInsertionRules) {
            insertionRuleMap += rule.take(2) to rule.last().toString()
        }

        val countingMap = mutableMapOf<String, Long>()
        for (string in insertionRuleMap.values.distinct()) {
            countingMap += Pair(string, 0L)
        }

        // initialize countingMap with values in polymer template
        polymerTemplate.forEach { countingMap[it] = countingMap[it]!! + 1 }

        val pairCountMap = mutableMapOf<String, Long>()
        for (key in insertionRuleMap.keys) {
            pairCountMap += Pair(key, 0L)
        }

        // initialize pairCountMap with pairs in polymer template
        for (pair in polymerTemplate.windowed(2).map { it.joinToString("") }) {
            pairCountMap[pair] = pairCountMap[pair]!! + 1
        }

        for (step in 1..40) {
            val pairCountSnapshot: Map<String, Long> = HashMap(pairCountMap)

            for (key in pairCountSnapshot.keys) {
                if (pairCountSnapshot[key]!! > 0) {

                    val newPolymer = insertionRuleMap[key]!!
                    val firstNewPair = "${key.first()}$newPolymer"
                    val secondNewPair = "$newPolymer${key.last()}"

                    pairCountMap[firstNewPair] = pairCountMap[firstNewPair]!! + pairCountSnapshot[key]!!
                    pairCountMap[secondNewPair] = pairCountMap[secondNewPair]!! + pairCountSnapshot[key]!!

                    countingMap[newPolymer] = countingMap[newPolymer]!! + pairCountSnapshot[key]!!

                    pairCountMap[key] = pairCountMap[key]!! - pairCountSnapshot[key]!!

                }
            }
        }

        return countingMap.values.maxOf { it } - countingMap.values.minOf { it }
    }



    // faster than first try, and can handle large counts, but not fast enough!
    fun part2secondTry(input: List<String>): Long {
        var polymerTemplate = input[0].chunked(1).toMutableList()
        val pairInsertionRules = input.dropWhile { it.contains("->").not() }

        val insertionRuleMap = mutableMapOf<String, String>()
        for (rule in pairInsertionRules) {
            insertionRuleMap += rule.take(2) to rule.last().toString()
        }

        val countingMap = mutableMapOf<String, Long>()

        for (string in insertionRuleMap.values.distinct()) {
            countingMap += Pair(string, 0L)
        }

        for (step in 1..25) {
            val newPolymer = mutableListOf<String>()

            for (i in 0 until polymerTemplate.size - 1) {
                val next = polymerTemplate[i]
                val nextNew = insertionRuleMap[arrayListOf(polymerTemplate[i], polymerTemplate[i + 1]).joinToString("")]!!
                newPolymer += next
                newPolymer += nextNew
                if (step == 25) {
                    countingMap[next] = countingMap[next]!! + 1
                    countingMap[nextNew] = countingMap[nextNew]!! + 1
                }
            }
            val last =polymerTemplate.last()
            newPolymer += last
            if (step == 25) {
                countingMap[last] = countingMap[last]!! + 1
            }

            polymerTemplate.clear()
            polymerTemplate.addAll(newPolymer)
        }

        return countingMap.values.maxOf { it } - countingMap.values.minOf { it }
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
    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}