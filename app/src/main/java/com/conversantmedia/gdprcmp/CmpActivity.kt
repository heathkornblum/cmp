package com.conversantmedia.gdprcmp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.widget.LinearLayout
import com.conversantmedia.gdprcmp.container.CmpWebView
import com.conversantmedia.gdprcmp.utils.ManifestReader

/**
 * Dialog-styled Activity for the CMP WebView.
 *
 * @author Mat Cooper
 * @author Heath Kornblum
 * @version 1.0
 * @updated 04/14/2020
 *
 */

internal class CmpActivity : Activity() {

    private lateinit var mView: CmpWebView
    private val nametag = javaClass.simpleName

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mView = CmpWebView(this, this)

        //Disable exiting cmp by taping outside the dialog.
        this.setFinishOnTouchOutside(false)

        val dimensions = widthHeightPair()

        this.setContentView(mView, LinearLayout.LayoutParams(dimensions.first, dimensions.second))
        val root = mView.rootView

        // Add rounded corners drawable background
        if (Build.VERSION.SDK_INT >= 21) {
            root.background = resources.getDrawable(R.drawable.cmp_shape, null)
        } else {
            root.background = resources.getDrawable(R.drawable.cmp_shape)
        }


    }

    /**
     * Destroy the CMP Widget during Pause events and let the parent view call it back up
     * during Resume.  Destroying this now prevents the parent view from calling multiple CMP
     * Widgets as a result of calling multiple Resume events.
     */

    override fun onPause() {
        mView.endCmp()
        super.onPause()
    }

    /**
     * Be sure to properly dismiss dialog to avoid leaky windows
     */

    override fun onDestroy() {
        mView.destroy()
        super.onDestroy()

    }

    /**
     * @returns a Pair(width, height)
     */
    private fun widthHeightPair() : Pair<Int, Int> {

        val metrics = resources.displayMetrics

        // Take width divided by height and return the ratio
        val deviceAspect : Float =
            (metrics.widthPixels.toFloat()/metrics.heightPixels.toFloat())

        var height = 0.0f
        var width = 0.0f

        if (deviceAspect > 1) {
            // landscape
            height = metrics.heightPixels.toFloat() * 0.85f
            width = 1.3f * height
        } else if (deviceAspect < 1) {
            // portrait
            height = metrics.heightPixels.toFloat() * 0.75f
            width = height / 1.3f
        } else {
            // square aspect ratio
            height = metrics.heightPixels.toFloat() * 0.85f
            width = height
        }
        return Pair(width.toInt(), height.toInt())
    }

    /**
    * When the hardware back key is pressed if there was web navigation all the
    * WebView to navigate back. If it cannot navigate back dismiss the CMP unless
    * prevented by the App Publisher.
     *
     * @param keyCode
     * @param event
    */

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mView.canGoBack()) {
                mView.goBack()
            } else {
                if (!ManifestReader(this).preventBack()) {
                    finish()
                }
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {

        /**
         * Companion Method to return intent required to start CmpActivity
         *
         * @param context
         */

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, CmpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }
}
