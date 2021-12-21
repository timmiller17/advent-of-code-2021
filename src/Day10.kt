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
                        score += scoreForPart1(")")
                        break
                    }
                    "[" -> stack += "["
                    "]" -> if (stack.last() == '[') {
                        stack = stack.dropLast(1)
                    } else {
                        score += scoreForPart1("]")
                        break
                    }
                    "{" -> stack += "{"
                    "}" -> if (stack.last() == '{') {
                        stack = stack.dropLast(1)
                    } else {
                        score += scoreForPart1("}")
                        break
                    }
                    "<" -> stack += "<"
                    ">" -> if (stack.last() == '<') {
                        stack = stack.dropLast(1)
                    } else {
                        score += scoreForPart1(">")
                        break
                    }
                }
            }
        }
        return score
    }

    fun part2(input: List<String>): Long {
        val scores = mutableListOf<Long>()
        val lines = input.map { it.chunked(1) }

        for (line in lines) {
            val( isLineCorrupt, completionString) = evaluateLine(line)
            if (isLineCorrupt.not()) {
                scores += scoreForPart2(completionString)
            }
        }

        return scores.sorted()[(scores.size / 2)]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

fun scoreForPart1(closingCharacter: String): Int {
    return when (closingCharacter) {
        ")" -> 3
        "]" -> 57
        "}" -> 1197
        ">" -> 25137
        else -> 0
    }
}

fun evaluateLine(line: List<String>): Pair<Boolean, String> {
    var stack = ""
    for (position in line.indices) {
        val charAtPosition = line[position]
        when (charAtPosition) {
            "(" -> stack += "("
            ")" -> if (stack.last() == '(') {
                stack = stack.dropLast(1)
            } else {
                return Pair(true, "")
            }
            "[" -> stack += "["
            "]" -> if (stack.last() == '[') {
                stack = stack.dropLast(1)
            } else {
                return Pair(true, "")
            }
            "{" -> stack += "{"
            "}" -> if (stack.last() == '{') {
                stack = stack.dropLast(1)
            } else {
                return Pair(true, "")
            }
            "<" -> stack += "<"
            ">" -> if (stack.last() == '<') {
                stack = stack.dropLast(1)
            } else {
                return Pair(true, "")
            }
        }
    }
    return Pair(false, stack.toCompletionString())
}

fun scoreForPart2(completionString: String): Long {
    var score = 0L

    for (bracket in completionString) {
        score *= 5
        when (bracket) {
            ')' -> score += 1
            ']' -> score += 2
            '}' -> score += 3
            '>' -> score += 4
        }
    }
    return score
}

fun String.toCompletionString(): String {
    var stack = this
    var mirrored = ""

    while (stack.isNotEmpty()) {
        when (stack.last()) {
            '(' -> mirrored += ")"
            '[' -> mirrored += "]"
            '{' -> mirrored += "}"
            '<' -> mirrored += ">"
        }
        stack = stack.dropLast(1)
    }
    return mirrored
}
