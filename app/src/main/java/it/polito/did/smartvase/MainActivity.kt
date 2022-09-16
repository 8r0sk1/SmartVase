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
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : NotificationCompat.Builder
    private val channelId = "com.example.vicky.notificationexample"
    private val description = "Test notification"

    //lateinit var alarmIntent: PendingIntent
    val fileName = "logged.txt"

    //@SuppressLint("ShortAlarm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*if(File(fileName).exists())
            writeInternalStorage("0;0")
        else*/
        /*if (File(fileName).length() == 0L)
            writeInternalStorage("0;0")
*/writeInternalStorage("0;0")

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        /*Intent(this, UpdateDBService::class.java).also { intent ->
            startService(intent)
        }


        val myIntent = Intent(this@MainActivity, gardenerService::class.java)
        alarmIntent = PendingIntent.getService(this@MainActivity, 0, myIntent, 0)
        val alarmMgr: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        //calendar.add(Calendar.SECOND, 3)
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
        Toast.makeText(baseContext, "Starting Service Alarm", Toast.LENGTH_LONG).show()

        alarmMgr?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            //AlarmManager.INTERVAL_FIFTEEN_MINUTES
            60000,
            alarmIntent
        )*/
    }

    fun writeInternalStorage(text: String) {
        val fileBody = text

        //Toast.makeText(baseContext, "read "+applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE).toString(), Toast.LENGTH_LONG).show()
        applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->

            output.write(fileBody.toByteArray())
        }
    }
    //TODO per leggere utente (activity as MainActivity).readUser()
    fun readUser() : List<String> {
        applicationContext.openFileInput(fileName).use { stream ->
            val text = stream.bufferedReader().use {
                it.readText()
            }
            return text.split(";")
        }
        return "0;0".split(";")
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

    fun vibration(short:Boolean) {
        val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if(short) vibratorService.vibrate(75)
        else vibratorService.vibrate(25)
    }
}