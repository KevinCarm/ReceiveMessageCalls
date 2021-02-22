package com.kevcs.receivemessagecalls.Receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.*
import android.widget.Toast
import com.kevcs.receivemessagecalls.MainActivity


class MyCallReceiver : BroadcastReceiver() {
    var number = ""

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        val state = intent!!.getStringExtra(EXTRA_STATE)
        if (state!! == EXTRA_STATE_RINGING || state == EXTRA_STATE_OFFHOOK) {
            number = intent.getStringExtra(EXTRA_INCOMING_NUMBER).toString()
            val i = Intent(context, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("number", number)
            i.putExtra("bundle", bundle)
            context?.startActivity(i)
        }
    }
}