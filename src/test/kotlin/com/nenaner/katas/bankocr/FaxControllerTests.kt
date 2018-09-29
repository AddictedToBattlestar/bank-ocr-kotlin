package com.nenaner.katas.bankocr

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.kotlintest.shouldBe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

val fakeDataLines =
        arrayOf("111222333444555666777888999",
                "111222333444555666777888999",
                "111222333444555666777888999",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXX")

object FaxControllerTests : Spek({
    given("a Fax Reader") {
        val mockFaxReader = mock<FaxReaderInterface>()
        whenever(mockFaxReader.readNextLine()).thenReturn(fakeDataLines[0]).thenReturn(fakeDataLines[1]).thenReturn(fakeDataLines[2]).thenReturn(fakeDataLines[3])
        val faxController = FaxController(mockFaxReader)
        on("reading the next character") {
            val returnValue = faxController.readNextCharacter()
            it("makes a call to the Fax reader to read 4 lines") {
                verify(mockFaxReader, times(4)).readNextLine()
            }
            it("returns the first character image") {
                returnValue shouldBe arrayOf("111","111","111")
            }
        }
    }
})
