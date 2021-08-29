package com.scorp.case1.viewModel

import android.os.AsyncTask
import android.util.Log

open class ErrorHandler(private var next: String) : AsyncTask<Void, Void, String>() {

    private var TAG: String = ErrorHandler::class.simpleName.toString()

    override fun doInBackground(vararg p0: Void?): String {  //retry implementation in background

        Log.d(TAG, "doInBackground next -> $next")
        Thread.sleep(1000)
        Controller.executeFetch(next)

        return next
    }
}