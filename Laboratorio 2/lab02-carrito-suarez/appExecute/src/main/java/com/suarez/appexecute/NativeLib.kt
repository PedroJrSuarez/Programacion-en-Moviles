package com.suarez.appexecute

class NativeLib {

    /**
     * A native method that is implemented by the 'appexecute' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'appexecute' library on application startup.
        init {
            System.loadLibrary("appexecute")
        }
    }
}