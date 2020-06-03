package tech.dsckiet.notif

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Broadcast():BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {


        val message = intent?.getStringExtra("key")
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}