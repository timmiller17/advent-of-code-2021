fun main() {
    fun part1(input: List<String>): Long {
        val initialSchool = input[0].split(",").map { it.toInt() }

        val schoolArray = longArrayOf(
            initialSchool.count { it == 0 }.toLong(),
            initialSchool.count { it == 1 }.toLong(),
            initialSchool.count { it == 2 }.toLong(),
            initialSchool.count { it == 3 }.toLong(),
            initialSchool.count { it == 4 }.toLong(),
            initialSchool.count { it == 5 }.toLong(),
            initialSchool.count { it == 6 }.toLong(),
            initialSchool.count { it == 7 }.toLong(),
            initialSchool.count { it == 8 }.toLong(),
        )

        var school = School(schoolArray)

        for (i in 0 until 80) {
            school = School(
                longArrayOf(
                    school.fish[1],
                    school.fish[2],
                    school.fish[3],
                    school.fish[4],
                    school.fish[5],
                    school.fish[6],
                    school.fish[7] + school.fish[0],
                    school.fish[8],
                    school.fish[0],
                )
            )
        }

        return school.totalInSchool()
    }

    fun part2(input: List<String>): Long {
        val initialSchool = input[0].split(",").map { it.toInt() }

        val schoolArray = longArrayOf(
            initialSchool.count { it == 0 }.toLong(),
            initialSchool.count { it == 1 }.toLong(),
            initialSchool.count { it == 2 }.toLong(),
            initialSchool.count { it == 3 }.toLong(),
            initialSchool.count { it == 4 }.toLong(),
            initialSchool.count { it == 5 }.toLong(),
            initialSchool.count { it == 6 }.toLong(),
            initialSchool.count { it == 7 }.toLong(),
            initialSchool.count { it == 8 }.toLong(),
        )

        var school = School(schoolArray)

        for (i in 0 until 256) {
            school = School(
                longArrayOf(
                    school.fish[1],
                    school.fish[2],
                    school.fish[3],
                    school.fish[4],
                    school.fish[5],
                    school.fish[6],
                    school.fish[7] + school.fish[0],
                    school.fish[8],
                    school.fish[0],
                )
            )
        }

        return school.totalInSchool()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

data class School(
    val fish: LongArray
) {
    override
    fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as School

        if (!fish.contentEquals(other.fish)) return false

        return true
    }

    override fun hashCode(): Int {
        return fish.contentHashCode()
    }

    fun totalInSchool() = fish.sum()
}
