package com.example.mymobilesyncwear

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService

private const val VOICE_TRANSCRIPTION_MESSAGE_PATH = "/voice_transcription"

class ListenerServiceFromWear : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == VOICE_TRANSCRIPTION_MESSAGE_PATH) {
            val startIntent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("VOICE_DATA", messageEvent.data)
            }
            startActivity(startIntent)
        }
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onDataChanged: $dataEvents")
        }

        // Loop through the events and send a message
        // to the node that created the data item.
        dataEvents.map { it.dataItem.uri }
            .forEach { uri ->
                // Get the node id from the host value of the URI
                val nodeId: String = uri.host?:""
                // Set the data of the message to be the bytes of the URI
                val payload: ByteArray = uri.toString().toByteArray()

                // Send the RPC
                Wearable.getMessageClient(this)
                    .sendMessage(nodeId, VOICE_TRANSCRIPTION_MESSAGE_PATH, payload)
            }
    }
}