package it.polito.did.smartvase.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import it.polito.did.smartvase.MainActivity
import it.polito.did.smartvase.R

class gardenerService :  Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)
        val path = applicationContext.getFilesDir()

        notification(R.drawable.nficusicon,"title","message")
        /*Toast.makeText(
            applicationContext, path.toString(),
            Toast.LENGTH_SHORT
        ).show()*/
        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }

    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : NotificationCompat.Builder
    private val channelId = "com.example.vicky.notificationexample"
    private val description = "Test notification"

    fun notification(imgID:Int, title: String, message:String){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val contentView = RemoteViews(packageName, R.layout.notification_layout)
        contentView.setTextViewText(R.id.tv_title,title)
        contentView.setTextViewText(R.id.tv_content,message)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId,description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this,channelId)
                .setContent(contentView)
                .setSmallIcon(imgID)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,imgID))
                .setContentIntent(pendingIntent)
        }else{

            builder = NotificationCompat.Builder(this)
                .setContent(contentView)
                .setSmallIcon(imgID)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,imgID))
                .setContentIntent(pendingIntent)
        }

        notificationManager.notify(1234,builder.build())
    }
}