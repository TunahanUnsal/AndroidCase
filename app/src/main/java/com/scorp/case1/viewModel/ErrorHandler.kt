package com.scorp.case1.viewModel

import android.os.AsyncTask
import android.util.Log
import com.scorp.case1.view.MainActivity
import java.lang.Exception
import java.util.logging.Handler

open class ErrorHandler(private var next: String) : AsyncTask<Void, Void, String>() {

    private var TAG: String = ErrorHandler::class.simpleName.toString()

    override fun doInBackground(vararg p0: Void?): String {

        Log.d(TAG, "doInBackground next -> $next")
        Thread.sleep(1000)
        Controller.executeFetch(next)

        return next
    }
}