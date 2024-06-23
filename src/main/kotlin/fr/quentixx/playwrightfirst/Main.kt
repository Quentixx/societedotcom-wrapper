package fr.quentixx.playwrightfirst

import com.github.crab2died.ExcelUtils
import com.github.crab2died.exceptions.Excel4JException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Usage example of the wrapper by creating Excel file to store company data.
 */
fun main() {

    // Collect data of 4 Internet service providers companies
    val resultList = SocieteDotComWrapper.multipleWrap(
        "https://www.societe.com/societe/bouygues-telecom-397480930.html",
        "https://www.societe.com/societe/orange-380129866.html",
        "https://www.societe.com/societe/free-421938861.html",
        "https://www.societe.com/societe/societe-francaise-du-radiotelephone-s-f-r-343059564.html"
    )

    print(resultList)

    // Export the result list as Excel file
    val sheetName = "Output_${getCurrentDateTime()}"
    val targetPath = "$sheetName.xlsx"
    try {
        ExcelUtils.getInstance().exportObjects2Excel(
            resultList,
            SocieteProfile::class.java, true, sheetName, true, targetPath
        )
    } catch (exception: Excel4JException) {
        exception.printStackTrace()
    }
}

fun getCurrentDateTime(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm")
    return currentDateTime.format(formatter)
}