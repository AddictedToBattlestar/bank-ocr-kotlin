package com.nenaner.katas.bankocr

interface FaxControllerInterface {
    fun readNextCharacter(): Array<String>?
}