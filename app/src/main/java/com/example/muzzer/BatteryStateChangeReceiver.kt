package com.example.muzzer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.widget.Toast
import androidx.core.content.ContextCompat.registerReceiver

class BatteryStateChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val batteryInput =


        val batteryStatus : Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
            context?.registerReceiver(null, it)
        }

        val batteryPercentage: Float? = batteryStatus?.let {intent->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level*100/scale.toFloat()
        }

        val isCharging = intent?.getBooleanExtra("state", false) ?: return
        if (isCharging) Toast.makeText(context, "Plugged in", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Not cahrging", Toast.LENGTH_SHORT).show()
    }
}