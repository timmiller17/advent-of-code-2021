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
    val testInput3 = readInput("Day16_test3")
    val testInput4 = readInput("Day16_test4")
    val testInput5 = readInput("Day16_test5")
    val testInput6 = readInput("Day16_test6")
    val testInput = readInput("Day16_test")
    check(part1(testInput1) == 9)
    check(part1(testInput2) == 14)
    check(part1(testInput3) == 16)
    check(part1(testInput4) == 12)
    check(part1(testInput5) == 23)
    check(part1(testInput6) == 31)
//    check(part1(testInput) == 7)
//    check(part2(testInput) == 5)

    val input = readInput("Day16")
//    println("part1 ${part1(input)}")
//    println("part2 ${part2(input)}")
}

fun String.padBinaryStringToFourDigits(): String {
    if (this.length < 4) {
        val padded = "0$this"
        return padded.padBinaryStringToFourDigits()
    }
    return this
}

fun process(versionSum: Int = 0, packet: String, numberOfSubpackets: Int = 0, numberOfBits: Int = 0): Pair<Int, String> {
    val remainingNumberOfSubpackets = numberOfSubpackets - 1
    val remainingNumberOfBits = numberOfBits
    if (packet.length < 11) { // minimum packet length is 11, which corresponds to a literal value packet that is 15 or less so only needs 4 bits
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
            if (remainingNumberOfSubpackets > 0) {
                val firstNewPacket = newPacket.drop(15).take(lengthInBits)
                val remainingPackets = newPacket.drop(15 + lengthInBits)
                // need to add both version from firstNewPacket and then whatever is in the additional subpackets as well
                return process(versionSum + version + process(0, firstNewPacket).first,
                    packet = remainingPackets,
                    numberOfSubpackets = remainingNumberOfSubpackets)
            } else if (remainingNumberOfBits > 0) {
                val firstNewPacket = newPacket.drop(15).take(lengthInBits)
                val remainingPackets = newPacket.drop(15 + lengthInBits)
                return process(versionSum + version + process(0, firstNewPacket).first,
                    packet = remainingPackets,
                    numberOfBits = newPacket.length - lengthInBits)
            } else {
                newPacket = newPacket.drop(15).take(lengthInBits)
                return process(versionSum + version, packet = newPacket, numberOfBits = lengthInBits)
            }
        } else {
            numberOfSubpackets = newPacket.take(11).toInt(2)
            newPacket = newPacket.drop(11)
            return process(versionSum + version, packet = newPacket, numberOfSubpackets = numberOfSubpackets)
        }
        // next do work to add versions if it's lengthTypeId == 1, keep processing while we have additional subpackets
        // this will test Day16_test2.txt input
        // now know that somehow I need to figure out a better check for no packets remaining when given a numberOfSubpackets type situation

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