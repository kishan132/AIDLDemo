package com.rntbci.serverapp

import android.content.ComponentName
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.rntbci.serverapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "kishan"
    private lateinit var binding: ActivityMainBinding

    private lateinit var mService: LocalService
    private var mBound:Boolean = false

    // Define callback for service binding, passed to bindService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            // we have bound to local service
            val binder = service as LocalService.LocalBinder
            mService = binder.getService()
            mBound = true

            Log.d(TAG, "onServiceConnected: ")
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
            Log.d(TAG, "onServiceDisconnected: ")
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.num.setOnClickListener {
            if(mBound){
                Log.d(TAG, "onCreate: button clicked")
                val num = mService.randomNumber
                Toast.makeText(this,"number $num", Toast.LENGTH_SHORT).show()
                binding.num.text = num.toString()
            }

        }

    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart: ")
        //Bind the local service
        /*Intent(this,LocalService::class.java).also { intent ->
            bindService(intent,connection,Context.BIND_AUTO_CREATE)
        }*/

    }

    override fun onStop() {
        super.onStop()
        //unbind service
        //unbindService(connection)
        mBound = false
    }
}