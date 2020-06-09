package com.conversantmedia.gdprcmp.utils

import com.conversantmedia.gdprcmp.config.CmpConfigObject
import com.conversantmedia.gdprcmp.utils.exceptions.CnvrCmpException
import org.json.JSONException
import org.json.JSONObject

/**
 * A utility for parsing Publisher-supplied JSON as Configuration
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 */
internal class JsonConfigReader {

    /**
     * Method for populating config data from JSON string passed on init.
     *
     * @param configJson string representation of the config to be used with the cmp
     *
     * @throws CnvrCmpException if there is a Json Error.
     */

    @Throws(CnvrCmpException::class)
    fun buildConfigFromJSON(configJson: String) {
        try {

            // Top level object
            val configJsonObject: JSONObject? = JSONObject(configJson)

            // Global Vendor List override for internal use only
            val gvlObject = configJsonObject?.optJSONObject("GVL")
            CmpConfigObject.CmpConfig.GVL.baseUrl = gvlObject?.optString("baseUrl")
            CmpConfigObject.CmpConfig.GVL.versioned = gvlObject?.optString("versionedFilename")
            CmpConfigObject.CmpConfig.GVL.latest =  gvlObject?.optString("latestFilename")

            // First tier data
            // Is Service Specific setting is always true for mobile, so it will not be set by the publisher
//            CmpConfigObject.CmpConfig.bannerLayout = configJsonObject?.optBoolean("bannerLayout")
            CmpConfigObject.CmpConfig.brandingImg = configJsonObject?.optString("brandingImg")
            CmpConfigObject.CmpConfig.countryCode = configJsonObject?.optString("countryCode")
            CmpConfigObject.CmpConfig.cssOverride = configJsonObject?.optString("cssOverride")
            CmpConfigObject.CmpConfig.gdprAppliesGlobally = configJsonObject?.optBoolean("gdprAppliesGlobally")
            CmpConfigObject.CmpConfig.language = configJsonObject?.optString("lang")
            CmpConfigObject.CmpConfig.legalName = configJsonObject?.optString("legalName")
            CmpConfigObject.CmpConfig.minShowDays = configJsonObject?.optInt("minShowDays")
            CmpConfigObject.CmpConfig.policyUrl = configJsonObject?.optString("policyUrl")
            CmpConfigObject.CmpConfig.version = configJsonObject?.optString("version")
            // Skipping JSON Config for inAppConfig as it should not be explicitly set by the publisher



            // Custom UI
            val customUIObject = configJsonObject?.optJSONObject("customUI")
            CmpConfigObject.CmpConfig.CustomUI.backgroundColor = customUIObject?.optString("backgroundColor")
            CmpConfigObject.CmpConfig.CustomUI.borderRadiusButton = customUIObject?.optString("borderRadiusButton")
            CmpConfigObject.CmpConfig.CustomUI.linkColor = customUIObject?.optString("linkColor")
            CmpConfigObject.CmpConfig.CustomUI.primaryColor = customUIObject?.optString("primaryColor")
            CmpConfigObject.CmpConfig.CustomUI.textColor = customUIObject?.optString("textColor")



            // Legal Bases
            val legalBasesObject = configJsonObject?.optJSONObject("legalBases")
            CmpConfigObject.CmpConfig.LegalBases.feature = legalBasesObject?.optJSONArray("feature")
            val legalBasesPurposeObject = legalBasesObject?.optJSONObject("purpose")
            CmpConfigObject.CmpConfig.LegalBases.purposeConsent = legalBasesPurposeObject?.optJSONArray("consent")
            CmpConfigObject.CmpConfig.LegalBases.purposeLegitimateInterest = legalBasesPurposeObject?.optJSONArray("legitimateInterest")
            CmpConfigObject.CmpConfig.LegalBases.specialFeature = legalBasesObject?.optJSONArray("specialFeature")
            CmpConfigObject.CmpConfig.LegalBases.specialPurpose = legalBasesObject?.optJSONArray("specialPurpose")


            // Text to replace any text in the CMP
            val textObject = configJsonObject?.optJSONObject("text")
            val landingTextObject = textObject?.optJSONObject("landing")
            val landingTextBodyObject = landingTextObject?.optJSONObject("body")
            val landingTextSeeMoreLinkObject = landingTextObject?.optJSONObject("seeMoreLink")
            val textReviewObject = textObject?.optJSONObject("review")
            val textReviewTabsObject = textReviewObject?.optJSONObject("tabs")
            val textReviewTabsCompaniesObject = textReviewTabsObject?.optJSONObject("companies")
            val textReviewTabsCompaniesHeaderObject = textReviewTabsCompaniesObject?.optJSONObject("header")
            val textReviewTabsPurposesObject = textReviewTabsObject?.optJSONObject("purposes")
            val textReviewTabsPurposesHeaderObject = textReviewTabsPurposesObject?.optJSONObject("header")
            val textReviewTabsPurposesLabelObject = textReviewTabsPurposesObject?.optJSONObject("label")
            val textReviewTabsPurposesLegalDescriptionObject = textReviewTabsPurposesObject?.optJSONObject("legalDescription")
            val textReviewTabsPurposesCompanyListObject = textReviewTabsPurposesObject?.optJSONObject("companyList")
            val textReviewButtonsObject = textReviewObject?.optJSONObject("buttons")
            val textReviewFooterObject = textReviewObject?.optJSONObject("footer")

            CmpConfigObject.CmpConfig.Text.Landing.Body.p1 = landingTextBodyObject?.optString("p1")
            CmpConfigObject.CmpConfig.Text.Landing.Body.p2 = landingTextBodyObject?.optString("p2")
            CmpConfigObject.CmpConfig.Text.Landing.cta = landingTextObject?.optString("cta")
            CmpConfigObject.CmpConfig.Text.Landing.reviewLink = landingTextObject?.optString("reviewLink")
            CmpConfigObject.CmpConfig.Text.Landing.SeeMoreLink.seeMore = landingTextSeeMoreLinkObject?.optString("seeMore")
            CmpConfigObject.CmpConfig.Text.Landing.SeeMoreLink.seeLess = landingTextSeeMoreLinkObject?.optString("seeLess")
            CmpConfigObject.CmpConfig.Text.Landing.title = landingTextObject?.optString("title")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.consent = textReviewTabsCompaniesHeaderObject?.optString("consent")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.feature = textReviewTabsCompaniesHeaderObject?.optString("feature")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.legInt = textReviewTabsCompaniesHeaderObject?.optString("legInt")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.specialPurpose = textReviewTabsCompaniesHeaderObject?.optString("specialPurpose")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.specialFeature = textReviewTabsCompaniesHeaderObject?.optString("specialFeature")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.privacyPolicyLink = textReviewTabsCompaniesObject?.optString("privacyPolicyLink")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.tabTitle = textReviewTabsCompaniesObject?.optString("tabTitle")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.consent = textReviewTabsPurposesHeaderObject?.optString("consent")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.feature = textReviewTabsPurposesHeaderObject?.optString("feature")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.legInt = textReviewTabsPurposesHeaderObject?.optString("legInt")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.specialFeature = textReviewTabsPurposesHeaderObject?.optString("specialFeature")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.specialPurpose = textReviewTabsPurposesHeaderObject?.optString("specialPurpose")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Label.feature = textReviewTabsPurposesLabelObject?.optString("feature")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Label.purpose = textReviewTabsPurposesLabelObject?.optString("purpose")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Label.specialFeature = textReviewTabsPurposesLabelObject?.optString("specialFeature")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Label.specialPurpose = textReviewTabsPurposesLabelObject?.optString("specialPurpose")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.LegalDescription.hide = textReviewTabsPurposesLegalDescriptionObject?.optString("hide")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.LegalDescription.show = textReviewTabsPurposesLegalDescriptionObject?.optString("show")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.CompanyList.hide = textReviewTabsPurposesCompanyListObject?.optString("hide")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.CompanyList.show = textReviewTabsPurposesCompanyListObject?.optString("show")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.CompanyList.no = textReviewTabsPurposesCompanyListObject?.optString("no")
            CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.tabTitle = textReviewTabsPurposesObject?.optString("tabTitle")
            CmpConfigObject.CmpConfig.Text.Review.Buttons.allow = textReviewButtonsObject?.optString("allow")
            CmpConfigObject.CmpConfig.Text.Review.Buttons.deny = textReviewButtonsObject?.optString("deny")
            CmpConfigObject.CmpConfig.Text.Review.Buttons.optOut = textReviewButtonsObject?.optString("optOut")
            CmpConfigObject.CmpConfig.Text.Review.Footer.allowAll = textReviewFooterObject?.optString("allowAll")
            CmpConfigObject.CmpConfig.Text.Review.Footer.denyAll = textReviewFooterObject?.optString("denyAll")
            CmpConfigObject.CmpConfig.Text.Review.Footer.cta = textReviewFooterObject?.optString("cta")
            CmpConfigObject.CmpConfig.Text.Review.introBody = textReviewObject?.optString("introBody")

        } catch (e: JSONException) {
            throw CnvrCmpException( "JSON config is an invalid CMP config: ${e.message}")
        }

    }
}