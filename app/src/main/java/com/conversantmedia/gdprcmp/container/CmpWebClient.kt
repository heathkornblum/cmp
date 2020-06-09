package com.conversantmedia.gdprcmp.container

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import java.net.URISyntaxException


/**
 * Class providing custom WebViewClient to be used with the CmpWebView
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 *
 */

internal class CmpWebClient : WebViewClient() {


    //Log error and close WebView if bootstrap script fails to load. (API 21+)
    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {

        if (Build.VERSION.SDK_INT >= 21) {
            val script: String = request?.url?.lastPathSegment.toString()
            val code: Int = errorResponse?.statusCode ?: 0

            //Should we just exit if any Http Error is thrown?
            if ((script.contentEquals("gdpr-cmp-bootstrap.js")) && ((code == 404) || (code >= 500))) {
                Log.e("HTTP ERROR", "$code when loading $script")
                view?.destroy()

            }
        }
        super.onReceivedHttpError(view, request, errorResponse)
    }

    //Log error and close WebView if bootstrap script fails to load. (API 19 and 20)
    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {

        if (Build.VERSION.SDK_INT < 21) {
            val script: String = failingUrl ?: ""
            val code: Int = errorCode

            //Should we just exit if any Http Error is thrown?
            if ((script.contentEquals("gdpr-cmp-bootstrap.js")) && ((code == 404) || (code >= 500))) {
                Log.e("HTTP ERROR", "$code when loading $script")
                view?.destroy()
            }

        }
        super.onReceivedError(view, errorCode, description, failingUrl)
    }

    //Handle intent URL for privacy policy. (API 21 +)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (Build.VERSION.SDK_INT >= 21) {
            val scheme = request?.url?.scheme?.toLowerCase()

            return when (scheme) {
                "intent" -> {
                    handleIntentUrl(view, request.url.toString())
                }
                else -> false
            }

        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    //Handle intent URL for privacy policy. (API earlier than 19 and 20)
    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (Build.VERSION.SDK_INT < 21) {
            val scheme = Uri.parse(url).scheme?.toLowerCase()

            return when (scheme) {
                "intent" -> {
                    handleIntentUrl(view, url)
                }
                else -> false
            }

        }
        return super.shouldOverrideUrlLoading(view, url)
    }

    /**
     * Function to handle intent:// scheme URLS for linking to privacy policy.
     *
     * @param view Web View that the URL is loaded in.
     * @param url the Intent scheme url
     *
     */

    private fun handleIntentUrl(view: WebView?, url: String?): Boolean {
        try {
            val context = view?.context
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)

            if (intent != null) {
                view?.stopLoading()


                val info = context?.packageManager?.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
                if (info != null) {
                    context.startActivity(intent)
                } else {
                    val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                    view?.loadUrl(fallbackUrl)
                }
                return true
            }
        } catch (e: URISyntaxException) {
            Log.e("URL parse Error", "Can't resolve intent://, $e")
        }
        return false
    }
}

