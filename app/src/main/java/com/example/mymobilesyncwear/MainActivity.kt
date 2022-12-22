package com.example.mymobilesyncwear

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btn_notify)
        val btnLocal = findViewById<Button>(R.id.btn_notify_local)
        btn.setOnClickListener {
            sendNotification()
        }
        btnLocal.setOnClickListener { sendNotification(true) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(local: Boolean = false) {
        val random = Random.nextInt(0,100)
        val group_key = "group_messages"
        val group_channel = "12345"
        val message = "This is an example with random number: $random"
        val notification_id = random
        val notificationManager = NotificationManagerCompat.from(this)

        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel =
            NotificationChannel(group_channel, group_key, importance)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager.createNotificationChannel(notificationChannel)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, group_channel)
            .setSmallIcon(android.R.mipmap.sym_def_app_icon)
            .setContentTitle(title)
            .setContentText(message)
            .setLocalOnly(local)
            .extend(NotificationCompat.WearableExtender()
                .setBridgeTag("foo"))

        val resultIntent = Intent(this, MainActivity::class.java)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent: PendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(resultPendingIntent)
        notificationManager.notify(notification_id, builder.build())
    }
}