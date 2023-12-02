package day1

import println
import readInput
import utils.Setup
import kotlin.math.*

val daySetup = Setup(1);

fun main() {
    fun getLineNumbers(line: String): Int{
        val firstDigit = line.first {
            it.isDigit()
        }.digitToInt()
        
        val lastDigit = line.last {
            it.isDigit()
        }.digitToInt()
        
        val number = (firstDigit.toString() + lastDigit.toString()).toInt()
        
        println(number)
        
        return number
    }
    
    
    fun part1(input: List<String>): Int {
        val numbers = mutableListOf<Int>()
        
        for (line in input) {
            numbers.add(getLineNumbers(line));
        }
        
        println(numbers.sum())
        
        return numbers.sum();
    }
    
   
    fun part2(input: List<String>): Int {
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
        
        // TODO: Replace each of the nbrString found using line.replaceRange(startIndex, endIndex) where startIndex is
        // TODO: given by the first param of the pair returned from the findAnyOf and the endIndex is the length of
        // TODO: the second pair param -1 | These should be replaced using the stringToNumber with the second pair param
        // TODO: as the index with .toString()
        
        for (line in input) {
            println(line.findAnyOf(stringNumberList, 0, true));
            println(line.findLastAnyOf(stringNumberList, line.length, true));
            
            for (nbrString in stringNumberList) {
                //println(nbrString)
                val newLine = line.replaceFirst(nbrString, stringToNumber[nbrString].toString(), true)
                // println(newLine)
            }
        }
        
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    //check(part1(daySetup.controlInput) == 142)
    
    // part1(daySetup.controlInput).println()
    //part1(daySetup.input).println()
    part2(daySetup.controlInputPart2).println()
    //part2(daySetup.input).println()
}
