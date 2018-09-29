package com.nenaner.katas.bankocr

import com.nhaarman.mockito_kotlin.mock
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
                "XXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "aaabbbcccdddeeefffggghhhiii",
                "aaabbbcccdddeeefffggghhhiii",
                "aaabbbcccdddeeefffggghhhiii"
        )

object FaxControllerTests : Spek({
    given("a Fax Reader") {
        val mockFaxReader = mock<FaxReaderInterface>()
        whenever(mockFaxReader.readNextLine())
                .thenReturn(fakeDataLines[0])
                .thenReturn(fakeDataLines[1])
                .thenReturn(fakeDataLines[2])
                .thenReturn(fakeDataLines[3])
                .thenReturn(fakeDataLines[4])
                .thenReturn(fakeDataLines[5])
                .thenReturn(fakeDataLines[6])
        val faxController = FaxController(mockFaxReader)
        on("trying to read the first character") {
            val returnValue = faxController.readNextCharacter()
            it("returns the first character image") {
                returnValue shouldBe arrayOf("111", "111", "111")
            }

        }
        on("trying to read the second character") {
            val returnValue = faxController.readNextCharacter()
            it("returns the second character image") {
                returnValue shouldBe arrayOf("222", "222", "222")
            }
        }
        on("trying to read the tenth character (on the next line") {
            for (i in 3..9) faxController.readNextCharacter()
            val returnValue = faxController.readNextCharacter()
            it("returns the tenth character image") {
                returnValue shouldBe arrayOf("aaa", "aaa", "aaa")
            }
        }
    }
})
