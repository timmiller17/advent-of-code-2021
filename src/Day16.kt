import java.lang.String.join

fun main() {
    fun part1(input: List<String>): Int {
        val code = input[0]

        val binaryCodes = code.map { Integer.toBinaryString(it.toString().toInt(16)).padBinaryStringToFourDigits() }

        val packet = binaryCodes.joinToString("")

        val (sum, newPacket) = process(packet = packet)

        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day16_test1")
    val testInput2 = readInput("Day16_test2")
    val testInput = readInput("Day16_test")
    check(part1(testInput1) == 9)
    check(part1(testInput2) == 14) // might double-check this is correct while developing
//    check(part1(testInput) == 7)
//    check(part2(testInput) == 5)

    val input = readInput("Day16")
    println("part1 ${part1(input)}")
    println("part2 ${part2(input)}")
}

fun String.padBinaryStringToFourDigits(): String {
    if (this.length < 4) {
        val padded = "0$this"
        return padded.padBinaryStringToFourDigits()
    }
    return this
}

fun process(versionSum: Int = 0, packet: String): Pair<Int, String> {
    if (packet.length < 3) {
        return Pair(versionSum, "")
    }
    val version = packet.take(3).toInt(2)
    var newPacket = packet.drop(3)
    val typeId = newPacket.take(3).toInt(2)
    newPacket = newPacket.drop(3)
    var intValue = 0
    var lengthTypeId: String
    var lengthInBits: Int
    var numberOfSubpackets: Int
    if (typeId == 4) {
        val (intValueLocal, newPacketLocal) = newPacket.literalValue()
        intValue = intValueLocal
        newPacket = newPacketLocal
        return process(versionSum + version, newPacket)
    } else {
        lengthTypeId = newPacket.take(1)
        newPacket = newPacket.drop(1)

        if (lengthTypeId == "0") {
            lengthInBits = newPacket.take(15).toInt(2)
            newPacket = newPacket.drop(15).take(lengthInBits)
            return process(versionSum + version, newPacket)

        } else {
            numberOfSubpackets = newPacket.take(11).toInt(2)
            newPacket = newPacket.drop(11)
        }
        // next do work to add versions if it's lengthTypeId == 1, keep processing while we have additional subpackets
        // this will test Day16_test2.txt input

    }
    return Pair(version, "")
}

fun String.literalValue(): Pair<Int, String> {
    var newPacket = this
    var value = ""

    while (newPacket[0] == '1') {
        value += newPacket.slice(1..4)
        newPacket = newPacket.drop(5)
    }
    value += newPacket.slice(1..4)
    return Pair(value.toInt(2), newPacket.drop(5))
}