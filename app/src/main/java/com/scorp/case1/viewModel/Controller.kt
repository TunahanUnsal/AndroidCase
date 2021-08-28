package com.scorp.case1.viewModel


import android.util.Log
import com.scorp.case1.model.DataSource
import com.scorp.case1.model.FetchCompletionHandler
import com.scorp.case1.model.Person


class Controller {


    companion object {

        lateinit var listUpdater: ListUpdater
        lateinit var fetchCompletionHandler: FetchCompletionHandler
        lateinit var list: List<Person>
        private var TAG: String = Controller::class.simpleName.toString()


        fun executeFetch(tuna: String?) {

            var z = tuna


            list = listOf()

            fetchCompletionHandler = { response, error ->


                Log.d(TAG, "executeFetch error : $error")

                if (response != null) {
                    list = response.people
                    z = response.next.toString()
                    listUpdater.listUpdate(list, z!!)

                }

            }

            val datasource = DataSource()

            datasource.fetch(null, fetchCompletionHandler)


        }

        fun registerListUpdater(listUpdate: ListUpdater) {

            listUpdater = listUpdate

        }

    }


}