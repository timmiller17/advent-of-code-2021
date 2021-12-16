fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input[0].split(",")
        val boards = input.slice(2 until input.size)
            .chunked(6)
            .map { it.take(5) }
            .map { board -> board.map { row -> row.chunked(3) .map { it.trim() } } }

        val transposedBoards: MutableList<List<List<String>>> = mutableListOf()

        for (board in boards) {
            var newBoard: MutableList<List<String>> = mutableListOf()
            for (i in 0..4) {
                val row: MutableList<String> = mutableListOf()
                for (j in 0..4) {
                    row += board[j][i]
                }
                newBoard += row
            }
            transposedBoards += newBoard
        }

        var winningBoard = -1
        var winningNumberIndex = -1
        outer@ for (i in 4 until numbers.size) {
            val calledNumbers = numbers.slice(0..i)

            for (board in boards.indices) {
                for (row in boards[board].indices) {
                    if (calledNumbers.containsAll(boards[board][row]) ||
                        calledNumbers.containsAll(transposedBoards[board][row])) {
                        winningBoard = board
                        winningNumberIndex = i
                        break@outer
                    }
                }
            }
        }

        val numbersOnWinningBoard = boards[winningBoard].flatten()
        val unmarkedNumbers = numbersOnWinningBoard
            .filterNot { numbers.slice(0..winningNumberIndex).contains(it) }

        return numbers[winningNumberIndex].toInt() * unmarkedNumbers.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(",")
        val boards = input.slice(2 until input.size)
            .chunked(6)
            .map { it.take(5) }
            .map { board -> board.map { row -> row.chunked(3) .map { it.trim() } } }

        val transposedBoards: MutableList<List<List<String>>> = mutableListOf()

        for (board in boards) {
            var newBoard: MutableList<List<String>> = mutableListOf()
            for (i in 0..4) {
                val row: MutableList<String> = mutableListOf()
                for (j in 0..4) {
                    row += board[j][i]
                }
                newBoard += row
            }
            transposedBoards += newBoard
        }

        var lastWinningNumberIndex = -1
        var lastWinningBoardIndex = -1
        var winningBoardIndices = mutableSetOf<Int>()
        outer@ for (i in 4 until numbers.size) {
            if (boards.size == winningBoardIndices.size) {
                break@outer
            }
            val calledNumbers = numbers.slice(0..i)

            check@for (board in boards.indices) {
                if (winningBoardIndices.contains(board)) {
                    continue@check
                }
                for (row in boards[board].indices) {
                    if (calledNumbers.containsAll(boards[board][row]) ||
                        calledNumbers.containsAll(transposedBoards[board][row])) {
                        winningBoardIndices += board
                        lastWinningBoardIndex = board
                    }
                }
            }
            lastWinningNumberIndex = i
        }

        val numbersOnLosingBoard = boards[lastWinningBoardIndex].flatten()
        val unmarkedNumbers = numbersOnLosingBoard
            .filterNot { numbers.slice(0..lastWinningNumberIndex).contains(it) }

        return numbers[lastWinningNumberIndex].toInt() * unmarkedNumbers.sumOf { it.toInt() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}