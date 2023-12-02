package day1

import println
import utils.Setup

val daySetup = Setup(1)

fun main() {
    fun getLineNumbers(line: String): Int{
        val firstDigit = line.first {
            it.isDigit()
        }.digitToInt()
        
        val lastDigit = line.last {
            it.isDigit()
        }.digitToInt()
        
        return (firstDigit.toString() + lastDigit.toString()).toInt()
    }
    
    fun getLineNumbers(firstLineNumber: String, lastLineNumber: String): Int {
        val firstDigit = firstLineNumber.first {
            it.isDigit()
        }.digitToInt()
        
        val lastDigit = lastLineNumber.last {
            it.isDigit()
        }.digitToInt()
        
        return (firstDigit.toString() + lastDigit.toString()).toInt()
    }
    
    fun processTextNumbers(input: List<String>): MutableList<Int> {
        val stringNumberList = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val stringToNumber = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )
        val processedNumbers = mutableListOf<Int>()
        
        for (line in input) {
            var firstNumberLine = ""
            val firstStringNumber = line.findAnyOf(stringNumberList, 0, true)
            var lastNumberLine = ""
            val lastStringNumber = line.findLastAnyOf(stringNumberList, line.length, true)
            
            firstStringNumber?.let {
                val rangeStartFirst = firstStringNumber.first
                val rangeEndFirst = firstStringNumber.second.length.let{(it + rangeStartFirst) - 1}

                firstNumberLine = line.replaceRange(rangeStartFirst..rangeEndFirst, stringToNumber[firstStringNumber.second].toString())
            }
            
            lastStringNumber?.let {
                val rangeStartSecond = lastStringNumber.first
                val rangeEndSecond = lastStringNumber.second.length.let {(it + rangeStartSecond) - 1}
                
                lastNumberLine = line.replaceRange(rangeStartSecond..rangeEndSecond, stringToNumber[lastStringNumber.second].toString())
            }
            
            if (firstNumberLine.isNotEmpty() && lastNumberLine.isNotEmpty()) {
                processedNumbers.add(getLineNumbers(firstNumberLine, lastNumberLine))
            } else {
                processedNumbers.add(getLineNumbers(line))
            }
        }
        
        return processedNumbers
    }
    
    fun part1(input: List<String>): Int {
        val numbers = mutableListOf<Int>()
        
        for (line in input) {
            numbers.add(getLineNumbers(line))
        }
        
        return numbers.sum()
    }
    
   
    fun part2(input: List<String>): Int {
        val processedNumbers = processTextNumbers(input)
        
        println(processedNumbers.sum())
        
        return input.size
    }
    
    //part1(daySetup.controlInput).println()
    //part1(daySetup.input).println()
    //part2(daySetup.controlInputPart2).println()
    part2(daySetup.input).println()
}