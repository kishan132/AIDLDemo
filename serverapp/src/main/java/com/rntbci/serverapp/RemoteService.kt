package com.rntbci.serverapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log
import com.rntbci.aidllibrary.ICallbackListener
import com.rntbci.aidllibrary.IMyAidlInterface
import com.rntbci.aidllibrary.MyObject

class RemoteService : Service() {

    private val TAG = "kishan"
    var callback: ICallbackListener? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: rs")
    }

    override fun onBind(intent: Intent): IBinder {
        // Return the interface.
        Log.d(TAG, "onBind: rs")
        return binder
    }


    private val binder = object : IMyAidlInterface.Stub() {

        override fun getPid(): Int {
            Log.d(TAG, "rs getPid: getting process id ${Process.myPid()}")
            return Process.myPid()
        }

        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String
        ) {
            // Does nothing.
        }

        override fun registerCallback(listener: ICallbackListener?) {
            callback = listener
        }

        override fun unregisterCallback(listener: ICallbackListener?) {
            callback = null
        }


        override fun saveObject(myObject: MyObject?) {
            Log.d(TAG, "server saveObject: $myObject")
            callback?.onObjectSaved(myObject)
        }


    }
}