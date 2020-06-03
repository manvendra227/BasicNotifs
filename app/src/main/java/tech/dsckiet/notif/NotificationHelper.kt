package tech.dsckiet.notif

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


public const val CHANNEL1_ID="Channel 1"
public const val CHANNEL2_ID="Channel 2"
class NotificationHelper():Application() {


    private lateinit var manager:NotificationManager //Iske liye bhut gali khai maine :(

    override fun onCreate() {
        super.onCreate()

        manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createChannel()

    }

    private fun createChannel() {

        //Channel were introduced to give user more control over type of Notification he wants to see
        //for version below api 26/Android 8/Oreo channel is available but for below old 1 channel notif exists
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            val channel1= NotificationChannel(CHANNEL1_ID,"Channel1",NotificationManager.IMPORTANCE_HIGH)
            channel1.description="This is channel 1"


            val channel2= NotificationChannel(CHANNEL2_ID,"Channel2",NotificationManager.IMPORTANCE_LOW)
            channel2.description="This is channel 2"

           manager.createNotificationChannel(channel1)
           manager.createNotificationChannel(channel2)
        }
    }
}