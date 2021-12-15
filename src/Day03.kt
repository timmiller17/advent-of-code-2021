fun main() {
    fun part1(input: List<String>): Int {
        val size = input.size
        val halfSize = size / 2
        val entryLength = input[0].length
        val totalOnes = IntArray(entryLength)
        var gammaString = ""
        var epsilonString = ""
        input.map { it.chunked(1) }
            .forEach {
                for (i in 0 until entryLength) {
                    totalOnes[i] += it[i].toInt()
                }
            }
        for (i in 0 until entryLength) {
            if (totalOnes[i] > halfSize) {
                gammaString += "1"
                epsilonString += "0"
            } else {
                gammaString += "0"
                epsilonString += "1"
            }
        }
        println("gamma:$gammaString, epsilon:$epsilonString")


        return Integer.parseInt(gammaString, 2) * Integer.parseInt(epsilonString, 2)
    }

    fun part2(input: List<String>): Int {
        val entryLength = input[0].length
        var consideredInput = input

        var i = 0
        while (consideredInput.size > 1) {
            var onesList = mutableListOf<String>()
            var zeroesList = mutableListOf<String>()

            consideredInput.forEach {
                    if (it.chunked(1)[i] == "1") {
                        onesList += it
                    } else {
                        zeroesList += it
                    }
                }

            consideredInput = if (onesList.size >= zeroesList.size) onesList else zeroesList
            i++
        }
        val oxygenGeneratorRating = consideredInput[0]
        println("oxygen generator rating:$oxygenGeneratorRating")

        i = 0
        consideredInput = input
        while (consideredInput.size > 1) {
            var onesList = mutableListOf<String>()
            var zeroesList = mutableListOf<String>()

            consideredInput.forEach {
                if (it.chunked(1)[i] == "1") {
                    onesList += it
                } else {
                    zeroesList += it
                }
            }

            consideredInput = if (zeroesList.size <= onesList.size) zeroesList else onesList
            i++
        }
        val co2ScrubberRating = consideredInput[0]
        println("CO2 scrubber rating:$co2ScrubberRating")

        return Integer.parseInt(oxygenGeneratorRating, 2) * Integer.parseInt(co2ScrubberRating, 2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

