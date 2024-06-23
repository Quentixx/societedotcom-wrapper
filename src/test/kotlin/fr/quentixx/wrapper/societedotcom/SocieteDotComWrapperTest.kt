package fr.quentixx.wrapper.societedotcom

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import com.microsoft.playwright.Page
import com.microsoft.playwright.Locator
import com.microsoft.playwright.TimeoutError
import fr.quentixx.playwrightfirst.PageParser
import kotlin.test.BeforeTest

class PageParserTest {

    private lateinit var pageParser: PageParser
    private lateinit var page: Page
    private lateinit var locator: Locator

    @BeforeTest
    fun setUp() {
        pageParser = PageParser()
        page = mock(Page::class.java)
        locator = mock(Locator::class.java)
    }

    @Test
    fun `test parseBasic with valid selector`() {
        val selector = "#testSelector"
        val expectedText = "Test Text"
        `when`(page.locator(selector)).thenReturn(locator)
        `when`(locator.textContent(any())).thenReturn(expectedText)

        val result = pageParser.parseBasic(page, selector)
        assertEquals(expectedText, result)
    }

    @Test
    fun `test parseBasic with timeout error`() {
        val selector = "#testSelector"
        `when`(page.locator(selector)).thenReturn(locator)
        `when`(locator.textContent(any())).thenThrow(TimeoutError(""))

        val result = pageParser.parseBasic(page, selector)
        assertEquals("Data not found for selector: $selector", result)
    }

    @Test
    fun `test parseOptionnal with valid selector`() {
        val selector = "#testSelector"
        val expectedText = "Test Text"
        `when`(page.locator(selector)).thenReturn(locator)
        `when`(locator.textContent(any())).thenReturn(expectedText)

        val result = pageParser.parseOptional(page, selector)
        assertEquals(expectedText, result)
    }

    @Test
    fun `test parseOptionnal with timeout error`() {
        val selector = "#testSelector"
        `when`(page.locator(selector)).thenReturn(locator)
        `when`(locator.textContent(any())).thenThrow(TimeoutError(""))

        val result = pageParser.parseOptional(page, selector)
        assertNull(result)
    }

    @Test
    fun `test parseDateCreated with valid date`() {
        val selector = "#rensjur > tbody > tr:nth-child(1)"
        val expectedText = "Some text with date 12-12-2020"
        `when`(page.locator(selector)).thenReturn(locator)
        `when`(locator.textContent()).thenReturn(expectedText)

        val result = pageParser.parseDateCreated(page)
        assertEquals("12-12-2020", result)
    }

    @Test
    fun `test parseDateCreated with no date`() {
        val selector = "#rensjur > tbody > tr:nth-child(1)"
        val expectedText = "Some text without a date"
        `when`(page.locator(selector)).thenReturn(locator)
        `when`(locator.textContent()).thenReturn(expectedText)

        val result = pageParser.parseDateCreated(page)
        assertEquals("Data not found for selector: $selector", result)
    }

    // Add similar tests for other methods if necessary

    // Helper method to set up mocks for parseBasic and parseOptionnal tests
    private fun setupMockForParseMethods(selector: String, expectedText: String, throwError: Boolean = false) {
        `when`(page.locator(selector)).thenReturn(locator)
        if (throwError) {
            `when`(locator.textContent(any())).thenThrow(TimeoutError(""))
        } else {
            `when`(locator.textContent(any())).thenReturn(expectedText)
        }
    }
}