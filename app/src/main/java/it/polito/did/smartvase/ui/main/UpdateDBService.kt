package it.polito.did.smartvase.ui.main

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import it.polito.did.smartvase.R
import java.lang.InterruptedException
import java.lang.Process
import java.lang.Thread
import java.util.*
import kotlin.concurrent.fixedRateTimer


class UpdateDBService : Service() {

    private val TAG = "AutoService"
    private var timer: Timer? = null
    private var task: TimerTask? = null

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)
        val path = applicationContext.getFilesDir()

        Log.d("backcazzo","hello world!")
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()

        val fixedRateTimer = fixedRateTimer(name = "hello-timer",
            initialDelay = 100, period = 100) {
            Log.d("backcazzo","hello world!")
        }
        try {
            Thread.sleep(1000)
        } finally {
            fixedRateTimer.cancel();
        }

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }
        return START_STICKY
    }


    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }
}