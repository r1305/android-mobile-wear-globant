package com.example.mymobilesyncwear

import android.content.Intent
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

private const val HELLO_WORLD_WEAR_PATH = "/voice_transcription"

class ListenerServiceFromWear : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {

        /*
         * Receive the message from wear
         */
        if (messageEvent.getPath().equals(HELLO_WORLD_WEAR_PATH)) {

            //For example you can start an Activity
            val startIntent = Intent(this, MainActivity::class.java)
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(startIntent)
        }
    }
}