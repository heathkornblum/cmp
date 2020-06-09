package com.conversantmedia.gdprcmp.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri

import com.conversantmedia.gdprcmp.config.CmpConfigObject
/**
 * A utility for parsing Publisher-supplied Manifest values as Configuration
 *
 * @param context
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 */

internal class ManifestReader(val context: Context) {

    private val TAG = javaClass.simpleName
    /**
     * Method for reading values from app manifest.
     *
     * @param key meta-data name for stored value in app manifest
     */

    private fun getMetaData ( key: String) : Any?{
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        return appInfo?.metaData?.get(key)
    }

    /**
     * Method for validating uri from manifest.
     *
     * @param uriName Meta-data Key for the uri in the app manifest.
     *
     * @return Uri or null if Uri is invalid.
     */

    private fun getUri(uriName: String): Uri?{
        val uri = Uri.parse(getMetaData(uriName).toString())
        return if (uri.isAbsolute) uri else { null }
    }


    /**
     * Returns cmpObject with all values from app manifest.
     *
     * @return cmpConfig with data from app manifest populated.
     */

    internal fun getConfigFromManifest() {
        CmpConfigObject.CmpConfig.GVL.latest = getUri("CNVR_CMP_GVL_Latest")?.toString()
        CmpConfigObject.CmpConfig.GVL.versioned = getUri("CNVR_CMP_GVL_Versioned")?.toString()
        CmpConfigObject.CmpConfig.GVL.baseUrl = getUri("CNVR_CMP_GVL_Base_URL")?.toString()

        CmpConfigObject.CmpConfig.CustomUI.backgroundColor = getMetaData("CNVR_CMP_Custom_UI_Background_Color")?.toString()
        CmpConfigObject.CmpConfig.CustomUI.borderRadiusButton = getMetaData("CNVR_CMP_Custom_UI_Border_Radius_Button")?.toString()
        CmpConfigObject.CmpConfig.CustomUI.linkColor = getMetaData("CNVR_CMP_Custom_UI_Link_Color")?.toString()
        CmpConfigObject.CmpConfig.CustomUI.primaryColor = getMetaData("CNVR_CMP_Custom_UI_Primary_Color")?.toString()
        CmpConfigObject.CmpConfig.CustomUI.textColor = getMetaData("CNVR_CMP_Custom_UI_Text_Color")?.toString()

        CmpConfigObject.CmpConfig.LegalBases.feature = Tools.stringToIntJSONArray(getMetaData("CNVR_CMP_Legal_Bases_Features")?.toString())
        CmpConfigObject.CmpConfig.LegalBases.purposeConsent = Tools.stringToIntJSONArray(getMetaData("CNVR_CMP_Legal_Bases_Purpose_Consent")?.toString())
        CmpConfigObject.CmpConfig.LegalBases.purposeLegitimateInterest = Tools.stringToIntJSONArray(getMetaData("CNVR_CMP_Legal_Bases_Purpose_Legitimate_Interest")?.toString())
        CmpConfigObject.CmpConfig.LegalBases.specialFeature = Tools.stringToIntJSONArray(getMetaData("CNVR_CMP_Legal_Bases_Special_Feature")?.toString())
        CmpConfigObject.CmpConfig.LegalBases.specialPurpose = Tools.stringToIntJSONArray(getMetaData("CNVR_CMP_Legal_Bases_Special_Purpose")?.toString())

        CmpConfigObject.CmpConfig.Text.Landing.Body.p1 = getMetaData("CNVR_CMP_Text_Landing_Body_P1")?.toString()
        CmpConfigObject.CmpConfig.Text.Landing.Body.p2 = getMetaData("CNVR_CMP_Text_Landing_Body_P2")?.toString()
        CmpConfigObject.CmpConfig.Text.Landing.cta = getMetaData("CNVR_CMP_Text_Landing_CTA")?.toString()
        CmpConfigObject.CmpConfig.Text.Landing.reviewLink = getMetaData("CNVR_CMP_Text_Landing_Review_Link")?.toString()
        CmpConfigObject.CmpConfig.Text.Landing.SeeMoreLink.seeMore = getMetaData("CNVR_CMP_Text_Landing_See_More")?.toString()
        CmpConfigObject.CmpConfig.Text.Landing.SeeMoreLink.seeLess = getMetaData("CNVR_CMP_Text_Landing_See_Less")?.toString()
        CmpConfigObject.CmpConfig.Text.Landing.title = getMetaData("CNVR_CMP_Text_Landing_Title")?.toString()

        CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.consent = getMetaData("CNVR_CMP_Text_Review_Tabs_Companies_Header_Consent")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.feature = getMetaData("CNVR_CMP_Text_Review_Tabs_Companies_Header_Feature")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.legInt = getMetaData("CNVR_CMP_Text_Review_Tabs_Companies_Header_Leg_Int")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.specialFeature = getMetaData("CNVR_CMP_Text_Review_Tabs_Companies_Header_Special_Purpose")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.Header.specialPurpose = getMetaData("CNVR_CMP_Text_Review_Tabs_Companies_Header_Special_Feature")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.privacyPolicyLink = getUri("CNVR_CMP_Text_Review_Tabs_Companies_Privacy_Policy_Link")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Companies.tabTitle = getMetaData("CNVR_CMP_Text_Review_Tabs_Companies_Tab_Title")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.consent = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Header_Consent")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.feature = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Header_Feature")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.legInt = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Header_Leg_Int")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.specialFeature = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Header_Special_Feature")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Header.specialPurpose = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Header_Special_Purpose")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Label.feature = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Label_Feature")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Label.purpose = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Label_Purpose")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Label.specialFeature = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Label_Special_Feature")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.Label.specialPurpose = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Label_Special_Purpose")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.LegalDescription.show = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Legal_Description_Show")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.LegalDescription.hide = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Legal_Description_Hide")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.CompanyList.hide = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Company_List_Hide")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.CompanyList.show = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Company_List_Show")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Tabs.Purposes.CompanyList.no = getMetaData("CNVR_CMP_Text_Review_Tabs_Purposes_Company_List_No")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Buttons.allow = getMetaData("CNVR_CMP_Text_Review_Buttons_Allow")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Buttons.deny = getMetaData("CNVR_CMP_Text_Review_Buttons_Deny")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Buttons.optOut = getMetaData("CNVR_CMP_Text_Review_Buttons_Opt_Out")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Footer.allowAll = getMetaData("CNVR_CMP_Text_Review_Footer_Allow_All")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Footer.denyAll = getMetaData("CNVR_CMP_Text_Review_Footer_Deny_All")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.Footer.cta = getMetaData("CNVR_CMP_Text_Review_Footer_CTA")?.toString()
        CmpConfigObject.CmpConfig.Text.Review.introBody = getMetaData("CNVR_CMP_Text_Review_Intro_Body")?.toString()

//        CmpConfigObject.CmpConfig.bannerLayout = getMetaData("CNVR_CMP_Banner_Layout")?.toString()?.toBoolean()
        CmpConfigObject.CmpConfig.brandingImg = getUri("CNVR_CMP_Branding_Img")?.toString()
        CmpConfigObject.CmpConfig.countryCode = getMetaData("CNVR_CMP_Country_Code")?.toString()
        CmpConfigObject.CmpConfig.cssOverride = getUri("CNVR_CMP_CSS_Override")?.toString()
        CmpConfigObject.CmpConfig.gdprAppliesGlobally = getMetaData("CNVR_CMP_GDPR_Applies_Globally")?.toString()?.toBoolean()
        CmpConfigObject.CmpConfig.language = getMetaData("CNVR_CMP_Lang")?.toString()
        CmpConfigObject.CmpConfig.legalName = getMetaData("CNVR_CMP_Legal_Name")?.toString()
        CmpConfigObject.CmpConfig.minShowDays = getMetaData("CNVR_CMP_Min_Show_Days")?.toString()?.toInt()
        CmpConfigObject.CmpConfig.onGVL = getMetaData("CNVR_CMP_On_GVL")?.toString()?.toBoolean()
        CmpConfigObject.CmpConfig.policyUrl = getUri("CNVR_CMP_Policy_URL")?.toString()
        CmpConfigObject.CmpConfig.vendors = Tools.stringToIntJSONArray(getMetaData("CNVR_CMP_Vendors")?.toString())
        CmpConfigObject.CmpConfig.version = getMetaData("CNVR_CMP_Config_Version")?.toString()
    }

    /**
     * Method to determine if the hardware back key should be allowed to
     * dismiss the CMP.
     *
     * @return boolean: true prevents hardware back, default false.
     */

    internal fun preventBack() : Boolean {
        return getMetaData("CNVR_CMP_Prevent_Key_Back").toString().toBoolean()
    }

}