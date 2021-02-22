package com.kevcs.receivemessagecalls

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kevcs.receivemessagecalls.Receiver.MyCallReceiver


class MainActivity : AppCompatActivity() {
    private lateinit var buttonSave: Button
    private lateinit var phoneNumber: EditText
    private lateinit var message: EditText
    private var number: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askForPermits()
        setup()
        try {
            val bundle = intent.getBundleExtra("bundle")
            number = bundle!!.getString("number")

            if (number == daoNumber(this).getLastRegister()?.get(0)) {
                sendAutomaticMessage()
            }

        } catch (e: Exception) {
            e.message?.let { Log.d("error", it) }
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun setup(): Unit {
        phoneNumber = findViewById(R.id.txtPhoneNumber)
        message = findViewById(R.id.txtMessage)
        buttonSave = findViewById(R.id.btnSave)
        buttonSave.setOnClickListener {
            daoNumber(this)
                .insert(
                    phoneNumber.text.toString(),
                    message.text.toString()
                )
            message.setText("")
            phoneNumber.setText("")
            Toast.makeText(
                this,
                "ultimo: " + daoNumber(this).getLastRegister()?.get(1),
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    private fun sendAutomaticMessage() {
        val sms: SmsManager = SmsManager.getDefault()
        sms.sendTextMessage(
            number, null,
            daoNumber(this).getLastRegister()?.get(1),
            null, null
        )
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
    }

    private fun askForPermits() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.SEND_SMS,
                    android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.READ_CALL_LOG
                ), 1000
            )
        }
    }
}