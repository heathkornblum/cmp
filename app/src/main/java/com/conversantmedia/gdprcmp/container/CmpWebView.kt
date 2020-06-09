package com.conversantmedia.gdprcmp.container

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.WebView
import com.conversantmedia.gdprcmp.CmpActivity
import com.conversantmedia.gdprcmp.config.CmpConfigObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.TimerTask


/**
 * Class providing custom Web View to be used with the CMP.
 * and for displaying the CMP.
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 *
 */

internal class CmpWebView(context: Context) : WebView(context.applicationContext) {

    private val mContext = context
    private var cmpJsInterface = CmpJsInterface(mContext, this)
    private val config = CmpConfigObject.CmpConfig
    private lateinit var webViewData: String
    private val html = BuildHtml()
    private var cmpActivity: CmpActivity? = null
    private val cookieManager = CookieManager.getInstance()
    private var closeWebViewTimer= Timer()
    private var changedCookieSettings = false
    private val TAG = javaClass.simpleName
    private val cmpWebView = this
    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    init {
        @SuppressLint("SetJavaScriptEnabled")

        //Disable device storage access.
        settings.javaScriptEnabled = true
        settings.allowFileAccess = false
        settings.allowContentAccess = false
        settings.allowFileAccessFromFileURLs = false
        settings.allowUniversalAccessFromFileURLs = false

        //Disable setting Cookies is cookies are currently accepted.
        if (cookieManager.acceptCookie()) {
            changedCookieSettings = true
            cookieManager.setAcceptCookie(false)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        //Verify what type of WebView is needed to apply the correct view settings.
        coroutineScope.launch {
            if (context !is CmpActivity) {
                createWebView(true)
            }
        }


    }

    /**
     * Public constructor for the WebView to be used when displaying the JS CMP.
     *
     * @param context
     * @param activity CmpActivity that will load the application context.
     *
     */

    constructor(context: Context, activity: CmpActivity) : this(context) {
        cmpActivity = activity

        coroutineScope.launch {
            createWebView(false)
        }
    }

    /**
     * Method for creating and configuring web view.
     *
     * @param checkRequired Boolean indicating if the WebView is going to be used to
     * determine if the CMP is required.
     *
     * @return returns the configured web view.
     */

    private suspend fun createWebView(checkRequired: Boolean) {

        withContext(Dispatchers.Main) {
            when (checkRequired) {
                true -> {

                    webViewData = html.checkRequired(CmpConfigObject.getConfigString(context.applicationContext).toString())
                    visibility = View.GONE
                    Log.d(TAG, webViewData)
                    //Set timeout in case of latency or error to close webview.
                    setDestroyTimeout(cmpWebView,10000)

                    //Destroy Web View on JS error.

                    webChromeClient = object: CmpWebChromeClient() {
                        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                            if(consoleMessage?.messageLevel() == ConsoleMessage.MessageLevel.ERROR){
                                Log.e(TAG, "Error with CMP execution, closing CMP")
                                this@CmpWebView.destroy()
                            }
                            return super.onConsoleMessage(consoleMessage)
                        }
                    }


                }
                false -> {

                    webViewData = html.display(CmpConfigObject.getConfigString(context.applicationContext).toString())

                    //Configure Viewport setting.
                    settings.setSupportZoom(true)
                    settings.useWideViewPort = true

                    //Configure Chrome Client to end the CMP activity if there is JS Error.
                    webChromeClient = object: CmpWebChromeClient() {
                        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                            if(consoleMessage?.messageLevel() == ConsoleMessage.MessageLevel.ERROR){
                                Log.e(TAG, "Error with CMP execution, closing CMP")
                                this@CmpWebView.endCmp()
                            }
                            return super.onConsoleMessage(consoleMessage)
                        }
                    }
                }
            }

            // cnvrMobileCmp is the Javascript namespace.  Might move it to a String Resource
            addJavascriptInterface(cmpJsInterface, "cnvrMobileCmp")
            webViewClient = CmpWebClient()

            // using a base URL prevents the webview from being loaded with a data url
            loadDataWithBaseURL("http://localhost/", webViewData, "text/html", "UTF-8", null)

        }



    }

    /**
     * Method for destroying CmpWebView after a set amount of time.
     *
     * @param view CmpView to be destroyed.
     */

    private fun setDestroyTimeout(view: CmpWebView,time: Long) {
       val task : TimerTask = object :TimerTask() {
           override fun run() {
               Log.w(TAG, "Timeout when determining if consent is required, exiting CMP")
               Handler(Looper.getMainLooper()).post {
                   view.destroy()
               }
           }
       }
        closeWebViewTimer.schedule(task, time)
    }

    /**
     * Method for properly properly ending CMP Activity.
     */

    fun endCmp(){
        cmpActivity?.finish()
    }

    /**
     * Override the destroy function to also dismiss the Cmp Activity if it exists
     * and to re-enable cookies.
     */

    override fun destroy() {
        //If we disabled cookies, re-enable them.
        if (changedCookieSettings) {
            cookieManager.setAcceptCookie(true)
        }

        //Cancel Any timers
        closeWebViewTimer.cancel()
        closeWebViewTimer.purge()

        super.destroy()

    }


}
