package br.com.fiap.acid.cap1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setupContent()
    }

    private fun setupContent() {
        val btnCrash = findViewById<Button>(R.id.btn_crash)
        val btnAction = findViewById<Button>(R.id.btn_action)

        btnCrash.setOnClickListener {
            doCrashAction()
        }

        btnAction.setOnClickListener {
            doAnalyticsEvent("Click")
        }
    }

    private fun doCrashAction() {
        throw RuntimeException("Crashou!")
    }

    private fun doAnalyticsEvent(event: String) {
        firebaseAnalytics.logEvent("Evento_$event", bundleOf())
    }
}