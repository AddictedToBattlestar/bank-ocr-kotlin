package com.nenaner.katas.bankocr

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.kotlintest.shouldBe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

object BankOcrControllerTests : Spek({
    given("a bank OCR Controller") {
        val imageList = mutableListOf<String>()
        imageMap.forEach { image, _ -> imageList.add(imageList.size, image) }
        val mockFaxReader = mock<FaxReader>()
        val bankOcrController = BankOcrController(mockFaxReader)
        on("scanning an image with a single character from the page") {
            imageMap.forEach { imageToTest, expectedValue ->
                run {
                    whenever(mockFaxReader.readNextCharacter()).thenReturn(imageToTest).thenReturn(null)
                    val result = bankOcrController.scan()
                    it("should return the right number") {
                        result shouldBe expectedValue
                    }
                }
            }
        }
        on("scanning an image with an invalid character on the page (\"\")") {
            whenever(mockFaxReader.readNextCharacter()).thenReturn("foo").thenReturn(null)
            val exception = assertThrows(NumberFormatException::class.java) {
                bankOcrController.scan()
            }
            it("should throw the appropriate error") {
                assertEquals(numberFormatExceptionMessageText, exception.message)
            }
        }
        on("scanning an image with multiple characters from the page (\"12\")") {
            whenever(mockFaxReader.readNextCharacter()).thenReturn(imageList[1]).thenReturn(imageList[2]).thenReturn(null)
            val result = bankOcrController.scan()
            it("should return the right number (\"12\")") {
                result shouldBe 12
            }
        }
    }
})