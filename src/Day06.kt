fun main() {
    fun part1(input: List<String>): Int {
        val initialSchool = input[0].split(",").map { it.toInt() }

        val schoolArray = arrayListOf(
            initialSchool.count { it == 0 },
            initialSchool.count { it == 1 },
            initialSchool.count { it == 2 },
            initialSchool.count { it == 3 },
            initialSchool.count { it == 4 },
            initialSchool.count { it == 5 },
            initialSchool.count { it == 6 },
            initialSchool.count { it == 7 },
            initialSchool.count { it == 8 },
        )

        var school = School(schoolArray.toIntArray())

        for (i in 0 until 80) {
            school = School(
                arrayListOf(
                    school.fish[1],
                    school.fish[2],
                    school.fish[3],
                    school.fish[4],
                    school.fish[5],
                    school.fish[6],
                    school.fish[7] + school.fish[0],
                    school.fish[8],
                    school.fish[0],

                ).toIntArray()
            )
        }

        return school.totalInSchool()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
//    check(part2(testInput) == 5)

    val input = readInput("Day06")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

data class School(
    val fish: IntArray = IntArray(9)
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
