// IMyAidlInterface.aidl
package com.rntbci.aidllibrary;

// Declare any non-default types here with import statements
import com.rntbci.aidllibrary.MyObject;
import com.rntbci.aidllibrary.ICallbackListener;

interface IMyAidlInterface {
    /** Request the process ID of this service. */
    int getPid();

    /** Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void registerCallback(ICallbackListener cb);

    void unregisterCallback(ICallbackListener cb);

    oneway void saveObject(in MyObject myObject);
}