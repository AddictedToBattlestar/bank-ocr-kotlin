package com.nenaner.katas.bankocr

import mu.KLogging

class FaxController(private val faxReader: FaxReaderInterface) : FaxControllerInterface {
    private var faxLine: List<String> = ArrayList()

    private var currentPosition = 0

    override fun readNextCharacter(): Array<String>? {
        logger.info { "FaxController.readNextCharacter called" }
        readNextFaxLineIfNecessary()
        return getNextCharacterFromFaxLine()
    }

    private fun readNextFaxLineIfNecessary() {
        if (faxLine.isEmpty() || currentPosition >= faxLine.get(0).length) {
            currentPosition = 0
            readNextFaxLine()
        }
    }

    private fun readNextFaxLine() {
        faxLine = listOf(
                faxReader.readNextLine(),
                faxReader.readNextLine(),
                faxReader.readNextLine(),
                faxReader.readNextLine())
    }

    private fun getNextCharacterFromFaxLine(): Array<String> {
        val nextCharacter = arrayOf(faxLine[0].substring(currentPosition, currentPosition + 3),
                faxLine[1].substring(currentPosition, currentPosition + 3),
                faxLine[2].substring(currentPosition, currentPosition + 3))
        currentPosition += 3
        return nextCharacter
    }

    companion object : KLogging()
}