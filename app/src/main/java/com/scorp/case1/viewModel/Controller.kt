package com.scorp.case1.viewModel


import android.annotation.SuppressLint
import android.util.Log
import com.scorp.case1.model.DataSource
import com.scorp.case1.model.FetchCompletionHandler
import com.scorp.case1.model.Person
import java.util.logging.Handler
import java.util.logging.LogRecord


class Controller(handler: () -> Unit) {


    companion object {

        lateinit var listUpdater: ListUpdater
        lateinit var fetchCompletionHandler: FetchCompletionHandler
        lateinit var list: List<Person>
        private var TAG: String = Controller::class.simpleName.toString()
        private val datasource = DataSource()


        fun executeFetch(next: String?) {

            var z = next
            var t : String = "null"

            list = listOf()

            fetchCompletionHandler = { response, error ->


                Log.d(TAG, "executeFetch error : $error")
                Log.d(TAG, "executeFetch response : $response")

                if (response != null) {
                    Log.d(TAG, "executeFetch: listUpdated")
                    list = response.people
                    z = response.next.toString()

                    t = error?.errorDescription ?: "null"

                    listUpdater.listUpdate(list, z!!, t)

                }
                else{
                    t = error?.errorDescription ?: "null"
                    z = "null"
                    list = listOf()
                    listUpdater.listUpdate(list, z!!, t)
                }

            }



            datasource.fetch(null, fetchCompletionHandler)


        }

        fun registerListUpdater(listUpdate: ListUpdater) {

            listUpdater = listUpdate

        }

        fun errorCodeWizard(error: String,next : String): Int {

            Log.d(TAG, "errorCodeWizard error -> $error")

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
        private fun retryFetch(next : String) {

            val loadingProducts : ErrorHandler

            loadingProducts =
            object : ErrorHandler(next) {
                override fun onPostExecute(result: String?) {
                    super.onPostExecute(result)


                    Log.e("Result", result!!)
                }
            }

            loadingProducts.execute()


        }


    }



}