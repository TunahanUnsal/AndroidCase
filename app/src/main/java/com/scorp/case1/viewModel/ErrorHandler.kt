package com.scorp.case1.viewModel

import android.os.AsyncTask
import android.util.Log
import java.lang.Exception
import java.util.logging.Handler

open class ErrorHandler(private var next: String) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg p0: Void?): String {

        Thread.sleep(1000)
        Controller.executeFetch(next)

        return next
    }
}