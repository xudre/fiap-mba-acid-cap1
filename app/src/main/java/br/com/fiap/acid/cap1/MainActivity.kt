package br.com.fiap.acid.cap1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val userDocument = "12345679-00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setupContent()
        setupRemoteConfig()
        configObservers()
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

    private fun setupRemoteConfig() {
        Firebase.remoteConfig.run {
            setConfigSettingsAsync(remoteConfigSettings {
                // Intervalo de tempo entre verificações para retornar novos valores do RemoteConfig
                minimumFetchIntervalInSeconds = 60
            })
        }
    }

    private fun configObservers() {
        Firebase
            .remoteConfig
            .fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    evaluateFeatureConfig()
                }
            }
    }

    private fun evaluateFeatureConfig() {
        val chaveMinhaFeature = "minha_feature"
        val remoteConfigMinhaFeature = Firebase.remoteConfig.getString(chaveMinhaFeature)
        val clFeature = findViewById<View>(R.id.cl_feature)

        Log.d(chaveMinhaFeature, remoteConfigMinhaFeature)

        clFeature.visibility = if (userDocument.endsWith(remoteConfigMinhaFeature)) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun doCrashAction() {
        throw RuntimeException("Crashou!")
    }

    private fun doAnalyticsEvent(event: String) {
        firebaseAnalytics.logEvent("Evento_$event", bundleOf())
    }
}