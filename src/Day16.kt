import java.util.*

fun main() {

    var versionSum = 0L

    fun process(bitList: LinkedList<Char>): Long {

        val version = popBits(3, bitList).toLong(2)
        versionSum += version
        val typeId = popBits(3, bitList).toInt(2)

        if (typeId == 4) {
            return parseLiteralValue(bitList)
        } else {
            val lengthTypeId = bitList.pop()
            val operatorResults = mutableListOf<Long>()

            if (lengthTypeId == '0') {
                // subpackets defined by number of bits
                val subpacketBits = popBits(15, bitList).toInt(2)
                val expectedSizeAfterSubpacketsProcessed = bitList.size - subpacketBits
                while (bitList.size > expectedSizeAfterSubpacketsProcessed) {
                    operatorResults.add(process(bitList))
                }
            } else {
                // subpackets defined by number of subpackets
                val numberOfSubpackets = popBits(11, bitList).toInt(2)
                repeat(numberOfSubpackets) {
                    operatorResults.add(process(bitList))
                }
            }
            return when (typeId) {
                0 -> operatorResults.sum()
                1 -> operatorResults.reduce { acc, i ->  acc * i }
                2 -> operatorResults.minOf { it }
                3 -> operatorResults.maxOf { it }
                5 -> if (operatorResults[0] > operatorResults[1]) 1 else 0
                6 -> if (operatorResults[0] < operatorResults[1]) 1 else 0
                7 -> if (operatorResults[0] == operatorResults[1]) 1 else 0
                else -> -1
            }
        }
    }

    fun part1(input: List<String>): Long {

        val code = input[0]

        val binaryCodes = code.map { Integer.toBinaryString(it.toString().toInt(16)).padBinaryStringToFourDigits() }

        val packet = binaryCodes.joinToString("")

        val bitList = LinkedList<Char>()

        bitList.addAll(packet.toCharArray().toList())

        versionSum = 0L

        process(bitList)

        return versionSum
    }

    fun part2(input: List<String>): Long {

        val code = input[0]

        val binaryCodes = code.map { Integer.toBinaryString(it.toString().toInt(16)).padBinaryStringToFourDigits() }

        val packet = binaryCodes.joinToString("")

        val bitList = LinkedList<Char>()

        bitList.addAll(packet.toCharArray().toList())

        versionSum = 0L

        return process(bitList)
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day16_test1")
    val testInput2 = readInput("Day16_test2")
    val testInput3 = readInput("Day16_test3")
    val testInput4 = readInput("Day16_test4")
    val testInput5 = readInput("Day16_test5")
    val testInput6 = readInput("Day16_test6")
    check(part1(testInput1) == 9L)
    check(part1(testInput2) == 14L)
    check(part1(testInput3) == 16L)
    check(part1(testInput4) == 12L)
    check(part1(testInput5) == 23L)
    check(part1(testInput6) == 31L)

    val testInput7 = readInput("Day16_test7")
    val testInput8 = readInput("Day16_test8")
    val testInput9 = readInput("Day16_test9")
    val testInput10 = readInput("Day16_test10")
    val testInput11 = readInput("Day16_test11")
    val testInput12 = readInput("Day16_test12")
    val testInput13 = readInput("Day16_test13")
    val testInput14 = readInput("Day16_test14")
    check(part2(testInput7) == 3L)
    check(part2(testInput8) == 54L)
    check(part2(testInput9) == 7L)
    check(part2(testInput10) == 9L)
    check(part2(testInput11) == 1L)
    check(part2(testInput12) == 0L)
    check(part2(testInput13) == 0L)
    check(part2(testInput14) == 1L)

    val input = readInput("Day16")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}


fun popBits(n: Int, bitList: LinkedList<Char>): String {
    var result = ""

    repeat(n) {
        result += bitList.pop()
    }

    return result
}

fun parseLiteralValue(bitList: LinkedList<Char>): Long {
    var binaryString = ""
    while (bitList.pop() == '1') {
        binaryString += popBits(4, bitList)
    }
    binaryString += popBits(4, bitList)
    return binaryString.toLong(2)
}

fun String.padBinaryStringToFourDigits(): String {
    if (this.length < 4) {
        val padded = "0$this"
        return padded.padBinaryStringToFourDigits()
    }
    return this
}