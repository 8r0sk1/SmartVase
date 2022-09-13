package it.polito.did.smartvase

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import it.polito.did.smartvase.ui.main.gardenerService

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : NotificationCompat.Builder
    private val channelId = "com.example.vicky.notificationexample"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        startService(Intent(applicationContext, gardenerService::class.java))
        setContentView(R.layout.activity_main)
        //nascondo la Action Bar (barra con il titolo dell'app)
        supportActionBar?.hide()
    }

    //TODO per usarlo negli altri fragment         (activity as MainActivity).notification(R.drawable.nficusicon,"title","message")
    fun notification(imgID:Int, title: String, message:String){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        val contentView = RemoteViews(packageName,R.layout.notification_layout)
        contentView.setTextViewText(R.id.tv_title,title)
        contentView.setTextViewText(R.id.tv_content,message)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
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

    public fun vibration(short:Boolean) {
        val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if(short) vibratorService.vibrate(75)
        else vibratorService.vibrate(25)
    }
}