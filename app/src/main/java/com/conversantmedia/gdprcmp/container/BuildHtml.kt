package com.conversantmedia.gdprcmp.container

import com.conversantmedia.gdprcmp.BuildConfig

/**
 * Class used to build html for the CMP WebView
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 *
 */

internal class BuildHtml {

    private val TAG = javaClass.simpleName
    private val newLine = System.lineSeparator()
    private val bootStrapScript = BuildConfig.CMP_URL

    /**
     * Returns the html required for the JS cmp to determine if consent is required
     *
     * @return the full HTML to be loaded into the web view
     */

    fun checkRequired(config: String): String {
        return  header +
                openScript + newLine +
                cmpConfig + config + newLine +
                injectScript() + newLine +
                closeScript +
                "<script src =\"$bootStrapScript\"></script>" + newLine +
                footer
    }

    /**
     * Returns the html required to Display the CMP in the web view
     *
     * @return the full HTML to be loaded into the web view
     */

    fun display(config: String): String {
        return  header +
                openScript + newLine +
                cmpConfig + config + newLine +
                injectScript(1) + newLine +
                closeScript +
                "<script src =\"$bootStrapScript\"></script>" + newLine +
                footer

    }

    /**
     * Object contains strings used for building the html for the config
     * Broken out for ease testing/development.
     */

    companion object {

        private const val header = "\n" +
                "<html>" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"

        private const val footer = "\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  </body>\n" +
                "</html>"

        private const val openScript = "<script>\n"

        private const val closeScript = "\n" +
                "</script>\n"

        private const val cmpConfig = "window.cmpConfig = "

        /**
         * This returns javascript for running __tcfapi functions
         *
         * @param checkOrShow If 0, return script for checking need for CMP UI.  If 1 or more,
         * return script for capturing TC Data from a UI display.
         */
        private fun injectScript(checkOrShow: Int = 0): String {
            var jScript = "\n" +
                    "addTCListener = function () {\n" +
                    "    __tcfapi('ping', 2, (pingReturn) => {\n" +
                    "             if (pingReturn && pingReturn.cmpStatus == \"loaded\")\n" +
                    "             {\n" +
                    "                __tcfapi('addEventListener', 2, function (tcData, success){\n" +
                    "                     if(success && tcData.eventStatus === 'tcloaded') {\n" +
                    "                             __tcfapi('removeEventListener', 2, (success) => {}, tcData.listenerId);\n" +
                    "                     }\n"
            if (checkOrShow == 0) jScript +=
                        "                     if(success && tcData.eventStatus === 'cmpuishown') {\n" +
                        "                           cnvrMobileCmp.loadCmp(true);\n" +
                        "                     } else {" +
                        "                           cnvrMobileCmp.loadCmp(false);\n" +
                        "                     }\n"
            if (checkOrShow > 0) {
                jScript += "                    if(success && tcData.eventStatus === 'useractioncomplete') {\n" +
                        "                         __tcfapi('removeEventListener', 2, (success) => {}, tcData.listenerId);\n" +
                        "                         __tcfapi('getInAppTCData', 2, (inAppTCData, success) => {\n" +
                        "                           if(success) {\n" +
                        "                             cnvrMobileCmp.storeCmpSdkId(inAppTCData.cmpId);\n" +
                        "                             cnvrMobileCmp.storeConfigVersion(window.cmpConfig.version);\n" +
                        "                             cnvrMobileCmp.storeCmpSdkVersion(inAppTCData.cmpVersion);\n" +
                        "                             cnvrMobileCmp.storePolicyVersion(inAppTCData.tcfPolicyVersion);\n" +
                        "                             cnvrMobileCmp.storeGdprApplies(inAppTCData.gdprApplies);\n" +
                        "                             cnvrMobileCmp.storeCountryCode(inAppTCData.publisherCC);\n" +
                        "                             cnvrMobileCmp.storePurposeOneTreatment(inAppTCData.purposeOneTreatment);\n" +
                        "                             cnvrMobileCmp.storeUseNonStandardStacks(inAppTCData.useNonStandardStacks);\n" +
                        "                             cnvrMobileCmp.storeTCString(inAppTCData.tcString);\n" +
                        "                             cnvrMobileCmp.storeVendorConsents(inAppTCData[\"vendor\"][\"consents\"]);\n" +
                        "                             cnvrMobileCmp.storeVendorConsents(inAppTCData.vendor.consents);\n" +
                        "                             cnvrMobileCmp.storeVendorLegitimateInterests(inAppTCData.vendor.legitimateInterests);\n" +
                        "                             cnvrMobileCmp.storePurposeConsents(inAppTCData.purpose.consents);\n" +
                        "                             cnvrMobileCmp.storePurposeLegitimateInterests(inAppTCData.purpose.legitimateInterests);\n" +
                        "                             cnvrMobileCmp.storeSpecialFeaturesOptIns(inAppTCData.specialFeatureOptins);\n" +
                        "                             for (position in inAppTCData.publisher.restrictions) {\n" +
                        "                               cnvrMobileCmp.storePublisherRestrictions(position, inAppTCData.publisher.restrictions[position]);\n" +
                        "                             }\n" +
                        "                             cnvrMobileCmp.storePublisherConsent(inAppTCData.publisher.consents);\n" +
                        "                             cnvrMobileCmp.storePublisherLegitimateInterests(inAppTCData.publisher.legitimateInterests);\n" +
                        "                             cnvrMobileCmp.storePublisherCustomPurposesConsents(inAppTCData.publisher.customPurpose.consents);\n" +
                        "                             cnvrMobileCmp.storePublisherCustomPurposesLegitimateInterests(inAppTCData.publisher.customPurpose.legitimateInterests);\n" +
                        "                             cnvrMobileCmp.cmpComplete();\n" +
                        "                           }\n" +
                        "                         });\n" +
                        "                    }\n"
            }

            jScript += "                });\n" +
                    "             } else if (pingReturn && (pingReturn.cmpStatus == \"stub\" || pingReturn.cmpStatus == \"loading\"))\n" +
                    "             {\n" +
                    "                setTimeout(addTCListener,500);\n" +
                    "             }\n" +
                    "    });\n" +
                    "};\n" +
                    "addPingListener = function () {\n" +
                    "    if (typeof __tcfapi === \"function\") {\n" +
                    "        addTCListener();\n" +
                    "    } else {\n" +
                    "        setTimeout(addPingListener,100);\n" +
                    "    }\n" +
                    "}\n" +
                    "window.addEventListener('load', (event) => {\n" +
                    "        addPingListener();\n" +
                    "});\n"
            return jScript
        }
    }
}