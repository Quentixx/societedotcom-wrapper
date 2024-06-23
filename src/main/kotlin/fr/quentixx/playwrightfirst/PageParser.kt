package fr.quentixx.playwrightfirst

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.TimeoutError

class PageParser {

    private val regexDateCreated = Regex("""(\d{2}-\d{2}-\d{4})""")

    companion object {
        const val BUSINESS_NAMES_FIELD = "Noms commerciaux"
        const val ADDRESS_FIELD = "Adresse postale"
        const val RCS_NUMBER_FIELD = "Numéro RCS"
        const val DECLARED_MAIN_ACTIVITY_FIELD = "Activité principale déclarée"
        const val RCS_STATUS_FIELD = "Statut RCS"
        const val INSEE_STATUS_FIELD = "Statut INSEE"
        const val REGISTRATION_DATE_RCS_FIELD = "Date d'immatriculation RCS"
        const val REGISTRATION_DATE_INSEE_FIELD = "Date d'enregistrement INSEE"
        const val PREVIOUS_YEAR_REVENUE_FIELD = "Chiffre d'affaires"
    }

    /**
     * Parses a basic text content from the page using a given selector.
     * @param page The Page object representing the web page.
     * @param selector The CSS selector or XPath expression to locate the element.
     * @return The text content found at the specified selector or an error message if not found.
     */
    fun parseBasic(page: Page, selector: String): String {
        var result: String
        try {
            val opt = Locator.TextContentOptions()
            opt.timeout = 1000.00
            val textContent = page.locator(selector).textContent(opt)
            result = textContent
        } catch (e: TimeoutError) {
            result = "Data not found for selector: $selector"
        }
        return result
    }

    /**
     * Parses optional text content from the page using a given selector.
     * @param page The Page object representing the web page.
     * @param selector The CSS selector or XPath expression to locate the element.
     * @return The text content found at the specified selector, or null if not found.
     */
    fun parseOptional(page: Page, selector: String): String? {
        var result: String?
        try {
            val opt = Locator.TextContentOptions()
            opt.timeout = 1000.00
            val textContent = page.locator(selector).textContent(opt)
            result = textContent
        } catch (e: TimeoutError) {
            result = null
        }
        return result
    }

    /**
     * Parses the date of creation of the company from the page.
     * @param page The Page object representing the web page.
     * @return The date of creation in the format "dd-mm-yyyy", or an error message if not found.
     */
    fun parseDateCreated(page: Page): String {
        val selector = "//*[@id=\"rensjur\"]/tbody/tr[1]/td[2]/div/span[1]"
        val textContent = page.locator(selector).textContent()
        val match = regexDateCreated.find(textContent)
        return match?.value ?: "Data not found for selector: $selector"
    }

    /**
     * Parses the legal form of the company from the page.
     * @param page The Page object representing the web page.
     * @return The legal form of the company or an error message if not found.
     */
    fun parseLegalForm(page: Page) = parseBasic(page, "#catjur-histo-description")

    /**
     * Parses the business names of the company from the page.
     * @param page The Page object representing the web page.
     * @return The business names of the company or an error message if not found.
     */
    fun parseBusinessNames(page: Page) = parseBasic(
        page,
        "//td[contains(text(), \"$BUSINESS_NAMES_FIELD\")]/following-sibling::td"
    )

    /**
     * Parses the address of the company from the page.
     * @param page The Page object representing the web page.
     * @return The address of the company or an error message if not found.
     */
    fun parseAddress(page: Page) = parseBasic(
        page,
        "//td[contains(text(), \"$ADDRESS_FIELD\")]/following-sibling::td"
    )

    /**
     * Parses the SIREN number of the company from the page.
     * @param page The Page object representing the web page.
     * @return The SIREN number of the company or an error message if not found.
     */
    fun parseSirenNumber(page: Page) = parseBasic(page, "#siren_number > span.copyNumber__copy")

    /**
     * Parses the SIRET number of the company from the page.
     * @param page The Page object representing the web page.
     * @return The SIRET number of the company or an error message if not found.
     */
    fun parseSiretNumber(page: Page) = parseBasic(page, "#siret_number")

    /**
     * Parses the VAT number of the company from the page.
     * @param page The Page object representing the web page.
     * @return The VAT number of the company, or null if not found.
     */
    fun parseVatNumber(page: Page) = parseOptional(page, "#tva_number > span.copyNumber__copy")

    /**
     * Parses the RCS number of the company from the page.
     * @param page The Page object representing the web page.
     * @return The RCS number of the company or an error message if not found.
     */
    fun parseRCSNumber(page: Page) = parseBasic(
        page,
        "//td[contains(text(), \"$RCS_NUMBER_FIELD\")]/following-sibling::td"
    )

    /**
     * Parses the NAF or APE activity code of the company from the page.
     * @param page The Page object representing the web page.
     * @return The NAF or APE activity code of the company or an error message if not found.
     */
    fun parseActivityCodeNAFOrAPE(page: Page) = parseBasic(page, "#ape-histo-description")

    /**
     * Parses the declared main activity of the company from the page.
     * @param page The Page object representing the web page.
     * @return The declared main activity of the company, or null if not found.
     */
    fun parseDeclaredMainActivity(page: Page) = parseOptional(
        page,
        "//td[contains(text(), \"$DECLARED_MAIN_ACTIVITY_FIELD\")]/following-sibling::td"
    )

    /**
     * Parses the collective agreement of the company from the page.
     * @param page The Page object representing the web page.
     * @return The collective agreement of the company, or null if not found.
     */
    fun parseCollectiveAgreement(page: Page) = parseOptional(page, "#idccdeduit_number")

    /**
     * Parses the RCS status of the company from the page.
     * @param page The Page object representing the web page.
     * @return The RCS status of the company, or null if not found.
     */
    fun parseRcsStatus(page: Page) = parseOptional(
        page,
        "//td[contains(text(), '$RCS_STATUS_FIELD')]/following-sibling::td//span[contains(@class, 'soSecondaryColor')]"
    )

    /**
     * Parses the INSEE status of the company from the page.
     * @param page The Page object representing the web page.
     * @return The INSEE status of the company, or null if not found.
     */
    fun parseInseeStatus(page: Page) = parseOptional(
        page,
        "//td[contains(text(), '$INSEE_STATUS_FIELD')]/following-sibling::td//span[contains(@class, 'soSecondaryColor')]"
    )

    /**
     * Parses the RCS registration date of the company from the page.
     * @param page The Page object representing the web page.
     * @return The RCS registration date of the company, or null if not found.
     */
    fun parseRegistrationDateRCS(page: Page) = parseOptional(
        page,
        "//td[contains(text(), \"$REGISTRATION_DATE_RCS_FIELD\")]/following-sibling::td"
    )

    /**
     * Parses the INSEE registration date of the company from the page.
     * @param page The Page object representing the web page.
     * @return The INSEE registration date of the company, or null if not found.
     */
    fun parseRegistrationDateInsee(page: Page) = parseOptional(
        page,
        "//td[contains(text(), \"$REGISTRATION_DATE_INSEE_FIELD\")]/following-sibling::td"
    )

    /**
     * Parses the employee number range of the company from the given web page.
     *
     * This function attempts to extract the employee number range from the specified CSS selectors
     * on the provided web page.
     *
     * @param page The Page object representing the web page to parse.
     * @return The employee number range of the company if found, otherwise null.
     */
    fun parseEmployeeNumber(page: Page) =
        parseOptional(page, "#trancheeff-histo-description") ?: parseOptional(page, "#effmoy-histo-description")

    /**
     * Parses the share capital of the company from the page.
     * @param page The Page object representing the web page.
     * @return The share capital of the company, or null if not found.
     */
    fun parseShareCapital(page: Page) = parseOptional(page, "#capital-histo-description")

    /**
     * Parses the turnover of the company from the page.
     * @param page The Page object representing the web page.
     * @return The turnover of the company, or null if not found.
     */
    fun parsePreviousYearRevenue(page: Page) = parseOptional(
        page,
        """//td[contains(text(), "$PREVIOUS_YEAR_REVENUE_FIELD")]/following-sibling::td/span[@class='numdisplay']"""
    )
}