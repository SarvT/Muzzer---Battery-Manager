package com.example.muzzer

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.muzzer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding
    lateinit var batteryReceiver: BatteryStateChangeReceiver
    lateinit var batteryInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        batteryReceiver = BatteryStateChangeReceiver()
        IntentFilter(Intent.ACTION_BATTERY_CHANGED).also {
            registerReceiver(batteryReceiver, it)
        }
        val batteryStatus : Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
            registerReceiver(null, it)
        }

        val batteryPercentage: Float? = batteryStatus?.let {intent->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level*100/scale.toFloat()
        }
        viewBinding.submitBtn.setOnClickListener {
//            var mediaPlayer = MediaPlayer.create(this, if (batteryPercentage.toString() == batteryInput) R.raw.battery_full else R.raw.battery_low)
            batteryInput = viewBinding.inpPercent.toString()

        }
        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging:Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        viewBinding.batteryStatusTV.text = if(isCharging) "Charging" else "Unplugged"
        viewBinding.batteryPercentTV.text = batteryPercentage.toString()


    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(batteryReceiver)
    }


}