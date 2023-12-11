package day3

import println
import utils.Setup

val daySetup = Setup(3)

// regex for digits: "^\\d+$"
// regex for everything except digits and ".": "^[^0-9\\.]+$"

data class AdjacentPosition (val row: Int, val index: Int, val positionName: String)

class EnginePieceNumber (
    private val rowNumber: Int,
    private val position: Int,
    val number: String,
    var isValidEnginePiece: Boolean = false,
    private val length: Int = number.length
) {
    val positions: MutableList<Int> = getAllPositions()
    val adjacentPositions: MutableList<AdjacentPosition> = getAllAdjacentPositions()
    private fun getAllPositions(): MutableList<Int> {
        val allPositions: MutableList<Int> = mutableListOf()
        
        for (i in 0 until length) {
            allPositions.add(position + i)
        }
        
        return allPositions
    }
    
    private fun getAllAdjacentPositions(): MutableList<AdjacentPosition> {
        val allAdjacentPositions: MutableList<AdjacentPosition> = mutableListOf()
        // Above
        allAdjacentPositions.add(AdjacentPosition(rowNumber - 1, position - 1, "Diag Above Left")) // diagonal left above
        allAdjacentPositions.add(AdjacentPosition(rowNumber - 1, position + length, "Diag Above Right")) // diagonal right above
        
        // Below
        allAdjacentPositions.add(AdjacentPosition(rowNumber + 1, position - 1, "Diag Below Left")) // diagonal left below
        allAdjacentPositions.add(AdjacentPosition(rowNumber + 1, position + length, "Diag Below Right")) // diagonal left below
        
        // Same Line
        allAdjacentPositions.add(AdjacentPosition(rowNumber, position - 1, "Before")) // before
        allAdjacentPositions.add(AdjacentPosition(rowNumber, position + length, "After")) // after
        
        for (index in positions) {
            allAdjacentPositions.add(AdjacentPosition(rowNumber + 1, index, "Directly Below")) // directly below
            allAdjacentPositions.add(AdjacentPosition(rowNumber - 1, index, "Directly Above")) // directly above
        }
        
        return allAdjacentPositions
    }
}

class Row (
    private val rowNumber: Int,
    val line: String
) {
    val mapOfItems: MutableList<EnginePieceNumber> = mapSchematic()
    private fun mapSchematic(): MutableList<EnginePieceNumber> {
        val mapOfItems: MutableList<EnginePieceNumber> = mutableListOf()
        val digitRegex = "\\d+".toRegex()
        val matches = digitRegex.findAll(line)
        
        for (match in matches) {
            val position = line.indexOf(match.value)
            val number = match.value
            
            mapOfItems.add(EnginePieceNumber(rowNumber, line.indexOf(match.value), match.value))
        }
        
        return mapOfItems
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val mapOfRows: MutableList<CharArray> = mutableListOf()
        val anySymbolButDotRegex = "[^0-9.]+".toRegex()
        val listOfAllEnginePieces: MutableList<Int> = mutableListOf()
        
        for(i in input.indices) {
            mapOfRows.add(input[i].toCharArray())
        }
        
        for (i in input.indices) {
            val row = Row(i, input[i])
            
            println("Line ($i):")
            for (enginePiece in row.mapOfItems) {
                enginePiece.adjacentPositions.removeAll { position ->
                    position.row < 0 || position.index < 0 || position.row > (input.size - 1) || position.index > (row.line.length - 1)
                }
                println("Adj. Positions: ${enginePiece.positions} - Number: ${enginePiece.number}")
                
                
                row.line.println()
                for (i in 0 until enginePiece.positions[0]) {
                    print('_')
                }
                print("^\n")
                
                enginePiece.adjacentPositions.sortWith(compareBy({ it.row }, { it.index }))
                // enginePiece.adjacentPositions.println()
                
                for (adjacentPosition in enginePiece.adjacentPositions) {
                    val currentAdjacentPositionValue = mapOfRows[adjacentPosition.row][adjacentPosition.index]
                    
                    println("Row: ${adjacentPosition.row} - Index: ${adjacentPosition.index} - Char: ${mapOfRows[adjacentPosition.row][adjacentPosition.index]} - Location: ${adjacentPosition.positionName}")
                    
                    if (currentAdjacentPositionValue.toString().matches(anySymbolButDotRegex)) {
                        enginePiece.isValidEnginePiece = true
                        listOfAllEnginePieces.add(enginePiece.number.toInt())
                    }
                }
            }
            println("-----------------------------")
        }
        
        listOfAllEnginePieces.println()
        
        listOfAllEnginePieces.sum().println()
        
        return 1
    }
    
    part1(daySetup.input)
}