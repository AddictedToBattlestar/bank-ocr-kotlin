package com.nenaner.katas.bankocr

import mu.KLogging

class FaxController(faxReader: FaxReaderInterface) : FaxControllerInterface {
    private val faxLine = arrayOf(
            faxReader.readNextLine(),
            faxReader.readNextLine(),
            faxReader.readNextLine(),
            faxReader.readNextLine())

    override fun readNextCharacter(): Array<String>? {
        logger.info { "FaxController.readNextCharacter called" }
        return arrayOf(faxLine[0].substring(0, 3),
                faxLine[1].substring(0, 3),
                faxLine[2].substring(0, 3))
    }

    companion object : KLogging()
}