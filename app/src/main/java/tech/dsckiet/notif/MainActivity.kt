package tech.dsckiet.notif

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val a = "This is Title"
    val b = "This is Description"
    lateinit var manager: NotificationManagerCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = NotificationManagerCompat.from(this)

        notif1.setOnClickListener {
            NotifHighPriority()
        }
        notif2.setOnClickListener {
            NotifLowPriority()
        }
        notif3.setOnClickListener {
            NotifMultiple()
        }
        notif4.setOnClickListener {
            NotifOpenActivityOnTap()
        }
        notif5.setOnClickListener {
            NotifAction()
        }
        notif6.setOnClickListener {
            NotifLargeText()
        }
    }




    //Basic Notification-Rings,Pops....
    private fun NotifHighPriority() {

        val notif = NotificationCompat.Builder(this, CHANNEL1_ID)
            .setSmallIcon(android.R.drawable.arrow_up_float)   //This small icon can be seen on status bar
            .setContentTitle(a)
            .setContentText(b)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        manager.notify(1, notif)

    }


    //Basic Notification -NO ring, no popup , can be seen on status bar
    private fun NotifLowPriority() {

        val notif = NotificationCompat.Builder(this, CHANNEL2_ID)
            .setSmallIcon(android.R.drawable.arrow_up_float)
            .setContentTitle(a)
            .setContentText(b)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        manager.notify(2, notif)
    }

    //Shows multiple notif
    private fun NotifMultiple() {

        val notif = NotificationCompat.Builder(this, CHANNEL1_ID)
            .setSmallIcon(android.R.drawable.arrow_up_float)   //This small icon can be seen on status bar
            .setContentTitle(a)
            .setContentText(b)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        manager.notify(3, notif)
        manager.notify(4, notif)
        manager.notify(5, notif)
        manager.notify(6, notif)
        //Here i have added 4 notifs of same type but diffrent id.Due to this i will be notified four time.Here you can add any business logic like looping and show messages.
        //If the ID's were same then notif will get overriden , so only 1 notif will be displayed
    }

    //Opens Activity on tapping
    private fun NotifOpenActivityOnTap() {


        //To create this,intent is required wrapped with pending intent so that it can only execute when user intend to do it.
        //Pending intent with getActivity is used to open activity on tap
        val i = Intent(this, MainActivity::class.java)
        val p = PendingIntent.getActivity(this, 0, i, 0)
        val notif = NotificationCompat.Builder(this, CHANNEL1_ID)
            .setSmallIcon(android.R.drawable.arrow_up_float)
            .setContentTitle(a)
            .setContentText(b)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(p)    // Pending intent is passed here in setContentIntent which allows it to open activity on tap
            .setAutoCancel(true)    //This allows the notif to disappear on tap to keep notif area clean.
            .build()

        manager.notify(7, notif)
    }


    //Does particular action like , call message, open activity or a simple toast
    private fun NotifAction() {
        //For this activity we require to make a broadcast class where we can basically write logic for action button
        //The broadcast class must be registered in manifest
        //pending intent-getBroadcast

        val i = Intent(this, MainActivity::class.java)
        val p = PendingIntent.getActivity(this, 0, i, 0)

        //Here create a intent for action button
        val bI = Intent(this, Broadcast::class.java)
        bI.putExtra("key", b)//Passing the message to display in toast
        val action = PendingIntent.getBroadcast(
            this,
            0,
            bI,
            PendingIntent.FLAG_UPDATE_CURRENT
        )//Flag is required to keep track of update in the message we pass, if message changes we need to update it in pending intent too


        val notif = NotificationCompat.Builder(this, CHANNEL1_ID)
            .setSmallIcon(android.R.drawable.arrow_up_float)
            .setContentTitle(a)
            .setContentText(b)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .addAction(
                R.drawable.setalarm,
                "ACTION 1",
                action
            )//Here is the action created, we can create max 3 of these. The icon is for api less than 26
            .build()

        manager.notify(8, notif)
    }


    //Large text notification generally used in mails
    //For this we need to use NotificationCompat styles
    //Few of them are Bigtext, InboxText,Mediatype,BigPictureType
    //This is implemented in .setStyle Attribute
    //Notification has 2 states collasped and expanded
    private fun NotifLargeText() {

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.setalarm)

        val notif: Notification =
            NotificationCompat.Builder(this, CHANNEL1_ID)
                .setSmallIcon(R.drawable.setalarm)//Visible in collapsed state
                .setContentTitle(a) //Visible in both states
                .setContentText(b)//Visible in collapsed state
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(getString(R.string.Dummy))
                        .setBigContentTitle("Large Content Title") //Visible on expanding
                        .setSummaryText("Summary Text here") //Visible on expanding
                )
                .setLargeIcon(bitmap) //Visible on expanding
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

        manager.notify(4, notif)

    }
}
