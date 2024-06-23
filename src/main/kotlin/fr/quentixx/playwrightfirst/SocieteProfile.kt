package fr.quentixx.playwrightfirst

import com.github.crab2died.annotation.ExcelField
import kotlinx.serialization.Serializable

/**
 * Data class to represent a company's profile given by the data provider.
 *
 * @property profileName - The company's profile name given by societe dot com
 * @property dateCreated - Date when the profile was created or last updated
 * @property legalForm - Legal form of the company
 * @property businessNames - Other business names the company is known by
 * @property address - Address of the company
 * @property sirenNumber - SIREN number of the company
 * @property siretNumber - SIRET number of the company
 * @property vatNumber - VAT number of the company (nullable)
 * @property rcsNumber - RCS number of the company
 * @property activityCodeNAFOrAPE - Activity code (NAF or APE) of the company
 * @property declaredMainActivity - Main declared activity of the company (nullable)
 * @property collectiveAgreement - Collective agreement applicable to the company (nullable)
 * @property rcsStatus - Status of the company in RCS (nullable)
 * @property inseeStatus - Status of the company in INSEE (nullable)
 * @property registrationDateRCS - Date of registration in RCS (nullable)
 * @property registrationDateInsee - Date of registration in INSEE (nullable)
 * @property employeeNumber - Number of employees in the company (nullable)
 * @property shareCapital - Share capital of the company (nullable)
 * @property previousYearRevenue - Revenue of the previous year for the company (nullable)
 */
@Serializable
data class SocieteProfile(

    @ExcelField(title = "Societe URL")
    val profileName: String,
    @ExcelField(title = "Date created")
    val dateCreated: String,
    @ExcelField(title = "Legal form")
    val legalForm: String,
    @ExcelField(title = "Business names")
    val businessNames: String,
    @ExcelField(title = "Address")
    val address: String,
    @ExcelField(title = "SIREN number")
    val sirenNumber: String,
    @ExcelField(title = "SIRET number")
    val siretNumber: String,
    @ExcelField(title = "VAT number")
    val vatNumber: String?,
    @ExcelField(title = "RCS number")
    val rcsNumber: String,
    @ExcelField(title = "Activity code NAF or APE")
    val activityCodeNAFOrAPE: String,
    @ExcelField(title = "Declared main activity")
    val declaredMainActivity: String?,
    @ExcelField(title = "Collective agreement")
    val collectiveAgreement: String?,
    @ExcelField(title = "RCS status")
    val rcsStatus: String?,
    @ExcelField(title = "Insee status")
    val inseeStatus: String?,
    @ExcelField(title = "Registration date RCS")
    val registrationDateRCS: String?,
    @ExcelField(title = "Registration date Insee")
    val registrationDateInsee: String?,
    @ExcelField(title = "Employee number")
    val employeeNumber: String?,
    @ExcelField(title = "Share capital")
    val shareCapital: String?,
    @ExcelField(title = "Previous year revenue")
    val previousYearRevenue: String?
)
