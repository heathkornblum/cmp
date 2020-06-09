package com.conversantmedia.gdprcmp.container

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.ConsoleMessage.MessageLevel
import android.webkit.WebChromeClient

/**
 * Class providing custom WebChromeClient to be used with the CmpWebView.
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 *
 */

open class CmpWebChromeClient: WebChromeClient(){

    //Specifically log warnings or errors for CMP JS issues in addition to the Chromium logs.
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        val level = consoleMessage?.messageLevel()
        val message = consoleMessage?.message()
        when(level) {
            MessageLevel.ERROR -> {
                Log.e(TAG, "CMP Javascript Error on line ${consoleMessage.lineNumber()}: $message")
            }
            MessageLevel.WARNING -> {
                Log.w(TAG, "CMP Javascript Warning on line ${consoleMessage.lineNumber()}: $message")
            }
            else -> {}
        }
        return super.onConsoleMessage(consoleMessage)
    }

    companion object {
        private const val TAG = "CNVR_CMP_WebView"
    }
}