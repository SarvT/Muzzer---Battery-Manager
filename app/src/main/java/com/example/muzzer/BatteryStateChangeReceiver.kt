package com.example.muzzer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.BatteryManager
import android.widget.Toast

class BatteryStateChangeReceiver(private val batteryInput:String) : BroadcastReceiver() {
//    private lateinit var listener: ChangeInterface
    lateinit var mediaPlayer:MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {

//        val batteryInput =
//        listener = context as ChangeInterface // initialse


        val batteryStatus : Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
            context?.registerReceiver(null, it)
        }

        val batteryPercentage: Float? = batteryStatus?.let { intent->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level*100/scale.toFloat()
        }
        val isCharging = intent?.getBooleanExtra("state", false) ?: return
        if (isCharging) Toast.makeText(context, "Plugged in", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Not cahrging", Toast.LENGTH_SHORT).show()

        if (batteryPercentage!!.toInt().toString() == batteryInput && isCharging) {
            mediaPlayer = MediaPlayer.create(context, R.raw.battery_full)
            mediaPlayer.start()
        } else if (batteryPercentage!! <10){
//            mediaPlayer = MediaPlayer.create(context, R.raw.battery_low)
        }
//            var mediaPlayer = MediaPlayer.create(this, if (batteryPercentage.toString() == batteryInput) R.raw.battery_full else R.raw.battery_low)

//        listener.returnPerc(batteryPercentage.toString())

    }
}
