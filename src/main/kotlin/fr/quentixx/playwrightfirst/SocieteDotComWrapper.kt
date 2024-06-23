package fr.quentixx.playwrightfirst

import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import mu.KotlinLogging

object SocieteDotComWrapper {

    private val logger = KotlinLogging.logger { }

    /**
     * Wrapper function to retrieve a SocieteProfile from a given URL.
     * Returns null if the profile name cannot be resolved.
     */
    fun wrap(url: String): SocieteProfile? {
        val profileName = getProfileName(url)

        if (profileName == null) {
            println("Can't resolve profileName for url: $url")
            return null
        }

        var resultFound: SocieteProfile?
        logger.info { "Starting to wrap $profileName..." }

        // Using Playwright to interact with the web page
        Playwright.create().use { playwright ->
            playwright.firefox().launch()
                .use {
                    it.newContext().newPage().use { page ->
                        page.navigate(url)
                        resultFound = parsePage(page, profileName)
                    }
                }
        }
        return resultFound
    }

    /**
     * Wrapper function to retrieve multiple SocieteProfiles from multiple URLs.
     * Returns a list of SocieteProfile objects for valid URLs.
     */
    fun multipleWrap(vararg urls: String): List<SocieteProfile> {
        return urls.mapNotNull { wrap(it) }
    }

    /**
     * Extracts the profile name from the given URL using regex.
     * Returns null if the URL format does not match.
     */
    fun getProfileName(url: String): String? {
        val regex = Regex("""https://www\.societe\.com/societe/([a-zA-Z0-9-]+)\.html""")
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(1)?.value
    }

    /**
     * Private function to parse the web page and extract SocieteProfile information.
     * Uses a PageParser object to retrieve specific data points.
     */
    private fun parsePage(page: Page, profileName: String): SocieteProfile {
        val parser = PageParser()

        val dateCreated = parser.parseDateCreated(page)
        val legalForm = parser.parseLegalForm(page)
        val businessNames = parser.parseBusinessNames(page)
        val address = parser.parseAddress(page)
        val sirenNumber = parser.parseSirenNumber(page)
        val siretNumber = parser.parseSiretNumber(page)
        val vatNumber = parser.parseVatNumber(page)
        val rcsNumber = parser.parseRCSNumber(page)
        val activityCodeNAFOrAPE = parser.parseActivityCodeNAFOrAPE(page)
        val declaredMainActivity = parser.parseDeclaredMainActivity(page)
        val collectiveAgreement = parser.parseCollectiveAgreement(page)
        val rcsStatus = parser.parseRcsStatus(page)
        val inseeStatus = parser.parseInseeStatus(page)
        val registrationDateRCS = parser.parseRegistrationDateRCS(page)
        val registrationDateInsee = parser.parseRegistrationDateInsee(page)
        val employeeNumber = parser.parseEmployeeNumber(page)
        val shareCapital = parser.parseShareCapital(page)
        val previousYearRevenue = parser.parsePreviousYearRevenue(page)

        return SocieteProfile(
            profileName, dateCreated,
            legalForm,
            businessNames,
            address,
            sirenNumber, siretNumber, vatNumber, rcsNumber,
            activityCodeNAFOrAPE, declaredMainActivity,
            collectiveAgreement, rcsStatus, inseeStatus,
            registrationDateRCS, registrationDateInsee,
            employeeNumber, shareCapital, previousYearRevenue
        )
    }
}
