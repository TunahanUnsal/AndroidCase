package com.scorp.case1.viewModel


import android.annotation.SuppressLint
import android.util.Log
import com.scorp.case1.model.DataSource
import com.scorp.case1.model.FetchCompletionHandler
import com.scorp.case1.model.Person
import kotlin.math.log


class Controller {


    companion object {

        lateinit var listUpdater: ListUpdater
        lateinit var fetchCompletionHandler: FetchCompletionHandler
        lateinit var list: List<Person>
        private var TAG: String = Controller::class.simpleName.toString()
        private lateinit var datasource : DataSource


        fun executeFetch(next: String?) {  //general control function for model

            datasource = DataSource()


            var tempNext = next
            var tempError = "null"

            list = listOf()

            fetchCompletionHandler = { response, error ->


                Log.d(TAG, "executeFetch error : $error")
                Log.d(TAG, "executeFetch response : $response")

                if (response != null) {
                    Log.d(TAG, "executeFetch: listUpdated")
                    list = response.people
                    tempNext = response.next.toString()

                    tempError = error?.errorDescription ?: "null"

                    listUpdater.listUpdate(list, tempNext!!, tempError)

                }
                else{
                    tempError = error?.errorDescription ?: "null"
                    tempNext = "null"
                    list = listOf()
                    listUpdater.listUpdate(list, tempNext!!, tempError)
                }

            }

            if(tempNext != "null"){  //next page available
                Log.d(TAG, "parametre: $next")
                datasource.fetch(next, fetchCompletionHandler)

            }else if (tempNext=="null" && tempError!="null"){  //an error occurred
                datasource.fetch(next, fetchCompletionHandler)
            }
            else{  //first execute
                datasource.fetch(null, fetchCompletionHandler)
            }

        }

        fun registerListUpdater(listUpdate: ListUpdater) {  //register for list update interface

            listUpdater = listUpdate

        }

        fun errorCodeWizard(error: String,next : String): Int {  //error handling function

            Log.d(TAG, "errorCodeWizard error -> $error")
            Log.d(TAG, "errorCodeWizard next -> $next")

            return when (error) {
                "Internal Server Error" -> {
                    Log.d(TAG, "errorCodeWizard -> Internal Server Error")
                    retryFetch(next)
                    return 1
                }
                "Parameter error" -> {
                    Log.d(TAG, "errorCodeWizard -> Parameter error")
                    retryFetch(next)
                    return 2
                }
                else -> {
                    Log.d(TAG, "errorCodeWizard -> return no error")
                    0
                }
            }
        }
        @SuppressLint("StaticFieldLeak")
        private fun retryFetch(next : String) {  //retry implementation

            val errorHandler : ErrorHandler

            errorHandler =
            object : ErrorHandler(next) {
                override fun onPostExecute(result: String?) {
                    super.onPostExecute(result)


                    Log.e("Result", result!!)
                }
            }

            errorHandler.execute()


        }


    }



}