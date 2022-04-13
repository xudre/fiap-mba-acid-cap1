package br.com.fiap.acid.cap1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupContent()
    }

    private fun setupContent() {
        val btnAction = findViewById<Button>(R.id.btn_action)

        btnAction.setOnClickListener {
            doCrashAction()
        }
    }

    private fun doCrashAction() {
        throw RuntimeException("Crashou!")
    }
}