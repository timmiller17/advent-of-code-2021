fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.map { it.chunked(1) }

        var score = 0

        for (line in lines.indices){
            var stack = ""
            for (position in lines[line].indices) {
                val charAtPosition = lines[line][position]
                when (charAtPosition) {
                    "(" -> stack += "("
                    ")" -> if (stack.last() == '(') {
                        stack = stack.dropLast(1)
                    } else {
                        score += scoreFor(")")
                        break
                    }
                    "[" -> stack += "["
                    "]" -> if (stack.last() == '[') {
                        stack = stack.dropLast(1)
                    } else {
                        score += scoreFor("]")
                        break
                    }
                    "{" -> stack += "{"
                    "}" -> if (stack.last() == '{') {
                        stack = stack.dropLast(1)
                    } else {
                        score += scoreFor("}")
                        break
                    }
                    "<" -> stack += "<"
                    ">" -> if (stack.last() == '<') {
                        stack = stack.dropLast(1)
                    } else {
                        score += scoreFor(">")
                        break
                    }
                }
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
//    check(part2(testInput) == 5)

    val input = readInput("Day10")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

fun scoreFor(closingCharacter: String): Int {
    return when (closingCharacter) {
        ")" -> 3
        "]" -> 57
        "}" -> 1197
        ">" -> 25137
        else -> 0
    }
}

