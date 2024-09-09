// ICallbackListener.aidl
package com.rntbci.aidllibrary;

// Declare any non-default types here with import statements
import com.rntbci.aidllibrary.MyObject;

interface ICallbackListener {
    void onObjectSaved(in MyObject myObject);
}