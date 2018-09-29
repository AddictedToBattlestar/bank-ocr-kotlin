package com.nenaner.katas.bankocr

const val numberFormatExceptionMessageText = "An invalid character was encountered in the fax document."
val imageMap = mapOf(
        arrayOf(" _ ",
                "| |",
                "|_|") to 0,
        arrayOf("   ",
                "  |",
                "  |") to 1,
        arrayOf(" _ ",
                " _|",
                "|_ ") to 2,
        arrayOf(" _ ",
                " _|",
                " _|") to 3,
        arrayOf("   ",
                "|_|",
                "  |") to 4,
        arrayOf(" _ ",
                "|_ ",
                " _|") to 5,
        arrayOf(" _ ",
                "|_ ",
                "|_|") to 6,
        arrayOf(" _ ",
                "  |",
                "  |") to 7,
        arrayOf(" _ ",
                "|_|",
                "|_|") to 8,
        arrayOf(" _ ",
                "|_|",
                " _|") to 9
)

class BankOcrController(private val faxController: FaxControllerInterface) {
    private val multiplier = 10

    fun scan(): Int? {
        var currentSum = 0
        var nextImage = faxController.readNextCharacter()
        while (nextImage != null) {
            val nextNumber = getNumberFromImage(nextImage)
            currentSum = currentSum * multiplier + nextNumber
            nextImage = faxController.readNextCharacter()
        }
        return currentSum
    }

    private fun getNumberFromImage(sourceImage: Array<String>): Int {
        return imageMap[sourceImage] ?: throw NumberFormatException(numberFormatExceptionMessageText)
    }
}