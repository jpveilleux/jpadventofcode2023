package day2

import utils.Setup

val daySetup = Setup(2)

class Pick (
    rawPick: String,
) {
    private val trimmedPick = getTrimmedPick(rawPick)
    val amount = trimmedPick[0].toInt()
    val color = trimmedPick[1]
    
    private fun getTrimmedPick(rawPick: String): List<String> {
        return rawPick.split(' ').filter {
            it.trim().isNotEmpty()
        }
    }
}
data class Round (
    val picks: MutableList<Pick>,
)

class Game(
    line: String
) {
    private val maxForColor = mapOf("red" to 12, "green" to 13, "blue" to 14)
    private val splitGameLine = line.split(':')
    val gameNumber = splitGameLine[0].split(' ')[1].toInt()
    private val gameRounds = parseGameRounds(splitGameLine[1].split(';'))
    val productOfRound = mapHighestNumberToColor().values.fold(1) {acc, value -> acc * value}
    
    private fun parseGameRounds(gameRoundsStrings: List<String>): List<Round> {
        val parsedGameRounds: MutableList<Round> = mutableListOf()
        
        for (i in gameRoundsStrings.indices) {
            val pickStrings = gameRoundsStrings[i].split(',')
            val picks: MutableList<Pick> = mutableListOf()
            
            for (j in pickStrings.indices) {
                picks.add(Pick(pickStrings[j]))
            }
            
            parsedGameRounds.add(Round(picks))
        }
        
        return parsedGameRounds
    }
    
    private fun mapHighestNumberToColor(): MutableMap<String,Int> {
        val colorToNumber = mutableMapOf<String,Int>()
        
        for (i in gameRounds.indices) {
            val round = gameRounds[i]
            
            for (j in round.picks.indices) {
                val pick = round.picks[j]
                
                if (colorToNumber.containsKey(pick.color)) {
                    if ((colorToNumber[pick.color] ?: 0) < pick.amount) {
                        colorToNumber[pick.color] = pick.amount
                    }
                } else {
                    colorToNumber[pick.color] = pick.amount
                }
            }
        }
        
        return colorToNumber
    }
    
    fun getIsGamePossible(): Boolean {
        for (j in gameRounds.indices) {
            val roundPicks = gameRounds[j].picks
            
            for (k in roundPicks.indices) {
                val pick = roundPicks[k]
                
                if (pick.amount > (maxForColor[pick.color] ?: 100)) { // This is ugly... I would be welcome to suggestions on how to handle this better
                    return false
                }
            }
        }
        
        return true
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val possibleGames: MutableList<Int> = mutableListOf()
        
        for (i in input.indices) {
            val game = Game(input[i])
            
            if (game.getIsGamePossible()) {
                possibleGames.add(game.gameNumber)
            }
        }
        
        return possibleGames.sum()
    }
    
    fun part2(input: List<String>): Int {
        val allProducts: MutableList<Int> = mutableListOf()
        for (i in input.indices) {
            val game = Game(input[i])
            
            allProducts.add(game.productOfRound)
        }
        
        return allProducts.sum()
    }
    
    println("######################")
    println("Part 1:")
    println("----------------------")
    println("Sum of winning games: ${part1(daySetup.input)}")
    println("######################")
    println("Part 2:")
    println("----------------------")
    println("Sum of all lowest needed numbers: ${part2(daySetup.input)}")
    println("######################")
}