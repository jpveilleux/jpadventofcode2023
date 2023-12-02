package utils

import readInput

class Setup (dayNumber: Int) {
    private var baseDir: String = "./day${dayNumber}/"
    private var controlPart1InputFileName: String = "${baseDir}Day${dayNumber}_control_part1"
    private var controlPart2InputFileName: String = "${baseDir}Day${dayNumber}_control_part2"
    private var inputFileName: String = "${baseDir}Day${dayNumber}"
    val controlInputPart1 = readInput(controlPart1InputFileName)
    val controlInputPart2 = readInput(controlPart2InputFileName)
    val input = readInput(inputFileName)
}