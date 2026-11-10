package com.titmazahit.aya

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {
    private lateinit var ayaView: AyaStageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ayaView = AyaStageView(this)
        setContentView(ayaView)
    }

    override fun onResume() {
        super.onResume()
        if (::ayaView.isInitialized) ayaView.startAnimation()
    }

    override fun onPause() {
        if (::ayaView.isInitialized) ayaView.stopAnimation()
        super.onPause()
    }
}
