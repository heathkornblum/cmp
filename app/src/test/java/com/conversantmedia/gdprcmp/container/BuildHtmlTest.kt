package com.conversantmedia.gdprcmp.container


import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BuildHtmlTest {


    private val build = BuildHtml()
    private val expectedRequire = "<html>  <head>\n" +
            "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<script>\n" +
            "window.cmpConfig = {\"gdprAppliesGlobally\":false,\"suppress\":false,\"localVendors\":{\"version\":\"default\",\"vendors\":{}},\"bannerPlacement\":\"none\",\"turnOffX\":true,\"inAppConfig\":{\"deviceId\":\"\",\"lvlVersion\":\"default\"}};\n" +
            "</script>\n" +
            "<script>\n" +
            "\n" +
            "</script>\n" +
            "<script> window.cmpConfig.inAppConfig.needsInAppConsent = function(a) {cnvrMobileCmp.loadCmp(a)} </script>\n" +
            "<script src=\"http://cdn-qa.mplxtms.com/gdpr/inapp/1.0.0/gdpr-cmp-bootstrap.js\"></script>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "  </body>\n" +
            "</html>"
    private val expectedDisplay = "<html>  <head>\n" +
            "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<script>\n" +
            "window.cmpConfig = {\"gdprAppliesGlobally\":false,\"suppress\":false,\"localVendors\":{\"version\":\"default\",\"vendors\":{}},\"bannerPlacement\":\"none\",\"turnOffX\":true,\"inAppConfig\":{\"deviceId\":\"\",\"lvlVersion\":\"default\"}};\n" +
            "</script>\n" +
            "<script>\n" +
            "\n" +
            "</script>\n" +
            "<script>\n" +
            "window.cmpConfig.callbacks = {};\n" +
            "window.cmpConfig.callbacks.onGDPRComplete = function() {\n" +
            "\t__cmp(\"getConsentData\", \"\", function(a) {cnvrMobileCmp.storeBitString(a.consentData);});\n" +
            "\t__cmp(\"getParsedPurposeConsents\", \"\", function(a) {cnvrMobileCmp.storeVendorPurpose(a);});\n" +
            "\t__cmp(\"getParsedVendorConsents\", \"\", function(a) {cnvrMobileCmp.storeVendorConsent(a);});\n" +
            "\t__cmp(\"getParsedLocalVendorConsents\", null, function(a){cnvrMobileCmp.storeLocalVendorConsents(a.consents);});\n" +
            "\t__cmp(\"getParsedLocalVendorConsents\", null, function(a){cnvrMobileCmp.storeLocalVendorVersion(a.version);});\n" +
            "\tcnvrMobileCmp.cmpComplete()\n" +
            "};\n" +
            "</script>\n" +
            "<script src=\"http://cdn-qa.mplxtms.com/gdpr/inapp/1.0.0/gdpr-cmp-bootstrap.js\"></script>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "  </body>\n" +
            "</html>"


    @Test
    fun checkRequired() {
////        val result = build.checkRequired()
//        assertEquals(expectedRequire, result)
    }

    @Test
    fun display() {
//        val result = build.display()
//        assertEquals(expectedDisplay, result)
    }
}