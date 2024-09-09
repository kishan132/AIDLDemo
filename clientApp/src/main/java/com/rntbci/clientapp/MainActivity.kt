package com.rntbci.clientapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Process
import android.util.Log
import com.rntbci.aidl_example.databinding.ActivityMainBinding
import com.rntbci.aidllibrary.ICallbackListener
import com.rntbci.aidllibrary.IMyAidlInterface
import com.rntbci.aidllibrary.MyObject

class MainActivity : AppCompatActivity() {

    private val TAG = "kishan"
    private lateinit var binding: ActivityMainBinding

    var iMyAidlInterface: IMyAidlInterface? = null

    val callback: ICallbackListener = object : ICallbackListener.Stub(){
        override fun onObjectSaved(p0: MyObject?) {
            Log.d(TAG, "client onObjectSaved callback: $p0")
        }
    }

    val mConnection = object : ServiceConnection {

        // Called when the connection with the service is established.
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // Following the preceding example for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service.
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service)

            iMyAidlInterface?.registerCallback(callback)

            Log.d(TAG, "onServiceConnected: ${iMyAidlInterface?.pid}")
            Log.d(TAG, "onServiceConnected: ${Process.myPid()}")


            iMyAidlInterface?.saveObject(MyObject(name = "kishan", age = 12))
        }

        // Called when the connection with the service disconnects unexpectedly.
        override fun onServiceDisconnected(className: ComponentName) {
            Log.d(TAG, "Service has unexpectedly disconnected")
            iMyAidlInterface = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindToService()

        Log.d(TAG, "onCreate: ${iMyAidlInterface?.pid}")
    }

    override fun onDestroy() {
        unbindService(mConnection)
        super.onDestroy()
    }

    private fun bindToService() {
        try {
            val intent = Intent("remoteService.AIDL")
            val explicitIntent = convertImplicitIntentToExplicitIntent(this,intent)
            Log.d(TAG, "bindToService: $explicitIntent")

            if (explicitIntent != null) {
                bindService(explicitIntent, mConnection, BIND_AUTO_CREATE)
            }
        } catch (e: Exception) {
            Log.i("bindToFiscalService", "e: $e")
        }
    }

    private fun convertImplicitIntentToExplicitIntent(ct: Context, implicitIntent: Intent): Intent? {
        val pm = ct.packageManager
        val resolveInfoList = pm.queryIntentServices(implicitIntent, 0)

        Log.d(TAG, "convertImplicitIntentToExplicitIntent: $resolveInfoList")

        if (resolveInfoList.size != 1) {
            return null
        }

        val serviceInfo = resolveInfoList[0]
        val component = ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name)
        val explicitIntent = Intent(implicitIntent)
        explicitIntent.component = component
        return explicitIntent
    }

}