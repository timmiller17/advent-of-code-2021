fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.split(" | ")[1] }
            .flatMap { it.split(" ")}
            .count { it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7 }
    }

    fun part2(input: List<String>): Int {
        val parsedInputs = input.map { it.split(" | ") }

        val digitMaps = parsedInputs.map { it[0].split(" ")
            .map { it.chunked(1) }
            .map { it.sorted() }
            .sortedBy { it.size }
        }.map { DigitMap(it) }

        val codes = parsedInputs.map { it[1].split(" ")
            .map { it.chunked(1) }
            .map { it.sorted() }
        }

        var total = 0

        for (i in codes.indices) {
            var stringRepresentation = ""
            for (digit in codes[i]) {
                stringRepresentation += digitMaps[i].digitLookup[digit]
            }
            total += stringRepresentation.toInt()
        }

        return total

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

data class DigitMap(val digitsList: List<List<String>>) {
    private var intermediateDigitLookup: MutableMap<String, List<String>> = mutableMapOf()

    var digitLookup: Map<List<String>, String>

    init {
        intermediateDigitLookup["1"] = digitsList[0]
        intermediateDigitLookup["7"] = digitsList[1]
        intermediateDigitLookup["4"] = digitsList[2]
        intermediateDigitLookup["8"] = digitsList[9]

        for (code in digitsList.slice(6..8)) {
            if (code.containsAll(intermediateDigitLookup["4"]!!)) {
                intermediateDigitLookup["9"] = code
            } else if (code.containsAll(intermediateDigitLookup["1"]!!)) {
                intermediateDigitLookup["0"] = code
            } else {
                intermediateDigitLookup["6"] = code
            }
        }

        for (code in digitsList.slice(3..5)) {
            if (code.containsAll(intermediateDigitLookup["1"]!!)) {
                intermediateDigitLookup["3"] = code
            } else if (intermediateDigitLookup["6"]!!.containsAll(code)) {
                intermediateDigitLookup["5"] = code
            } else {
                intermediateDigitLookup["2"] = code
            }
        }

        // had to initially create it as Map<String, List<String>> for building logic
        // however, for using it in the next step it requires Map<List<String>, String>
        // so this line is just swapping the keys / values
        // from: https://stackoverflow.com/a/45380326
        digitLookup = intermediateDigitLookup.entries.associateBy({ it.value }) { it.key }
    }
}