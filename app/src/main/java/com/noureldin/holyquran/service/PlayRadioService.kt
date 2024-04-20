package com.noureldin.holyquran.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.noureldin.holyquran.R


class PlayRadioService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return MyBinder()
    }

    inner class MyBinder: Binder(){
        fun getService(): PlayRadioService{
            return this@PlayRadioService
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val urlToPlay = intent?.getStringExtra("url")
        val name = intent?.getStringExtra("name")
        val id = intent?.getIntExtra("id",0)

        val action = intent?.getStringExtra("action")

        if (urlToPlay != null && name != null&& id!=null) {
            startMediaPlayer(urlToPlay, name,id)
        }

        if (action != null)
            Log.e(TAG, "onStartCommand: action=$action")
        when (action) {
            Play_Action,Pause_Action-> playPauseMediaPlayer()
            Stop_Action -> stopMediaPlayer()
        }
        return super.onStartCommand(intent, flags, startId)

    }
    fun playPauseMediaPlayer() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        } else {
            mediaPlayer.pause()
        }

        updateNotification()
    }

    fun pauseMediaPlayer() {
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()

    }




    fun stopMediaPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }

        stopForeground(true)
        stopSelf()


    }
    var name = ""
    var id = -1
    private var mediaPlayer = MediaPlayer()

    fun startMediaPlayer(urlToPlay: String, name: String,id: Int) {
        Log.e(TAG, "startMediaPlayer: name=$name")
        pauseMediaPlayer()//pause if playing
        if (this.name==name){
            playPauseMediaPlayer()
        }else{
            this.name = name
            this.id=id
            mediaPlayer= MediaPlayer()

            mediaPlayer.setDataSource(this, Uri.parse(urlToPlay))
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: MediaPlayer?) {
                    mp?.start()
                    createNotificationForMediaPlayer(name)

                }

            })




        }


    }
    @SuppressLint("ForegroundServiceType")
    private fun createNotificationForMediaPlayer(name: String) {
        //notification view
        val notificationView = RemoteViews(packageName, R.layout.notification_view)
        notificationView.setTextViewText(R.id.title,"Islami App Radio")
        notificationView.setTextViewText(R.id.description,name)
        notificationView.setImageViewResource(R.id.play,if (mediaPlayer.isPlaying) R.drawable.icon_pause else R.drawable.icon_play)
        notificationView.setOnClickPendingIntent(R.id.play,getPlayButtonPendingIntent())
        //notification
        val notification =  NotificationCompat.Builder(this,MyApplication.RADIO_PLAYER_NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.quran)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationView)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSound(null)
            .build()

        startForeground(1,notification)


    }
    fun getIsplaying():Boolean{
        return mediaPlayer.isPlaying
    }

    private fun updateNotification() {
        val notificationView = RemoteViews(packageName, R.layout.notification_view)
        notificationView.setTextViewText(R.id.title,"Islami App Radio")
        notificationView.setTextViewText(R.id.description,name)

        notificationView.setImageViewResource(R.id.play,if (mediaPlayer.isPlaying) R.drawable.icon_pause else R.drawable.icon_play)
        notificationView.setOnClickPendingIntent(R.id.play,getPlayButtonPendingIntent())
        val notification =  NotificationCompat.Builder(this,MyApplication.RADIO_PLAYER_NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.quran)
            .setStyle(NotificationCompat
                .DecoratedCustomViewStyle())
            .setCustomContentView(notificationView)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSound(null)
            .build()
        mediaPlayerStateListener?.onMediaPlayerStateChanged(id,mediaPlayer.isPlaying)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1,notification)

    }
    val Play_Action="play"
    val Pause_Action="pause"
    val Stop_Action="stop"

    private fun getPlayButtonPendingIntent(): PendingIntent {
        val intent = Intent(this, PlayRadioService::class.java)
        if (mediaPlayer.isPlaying)
            intent.putExtra("action", Pause_Action)
        else
            intent.putExtra("action", Play_Action)
        return PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopMediaPlayer()
    }

    companion object {
        private const val TAG = "PlayRadioService"
    }
    var mediaPlayerStateListener:MediaPlayerStateListener?=null

}
fun interface MediaPlayerStateListener{
    fun onMediaPlayerStateChanged(id:Int,isPlayed:Boolean)
}