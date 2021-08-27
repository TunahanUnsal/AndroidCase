package com.scorp.case1.ViewModel


import com.scorp.case1.Model.DataSource
import com.scorp.case1.Model.FetchCompletionHandler
import com.scorp.case1.Model.Person


class Controller {


    companion object {

        lateinit var listUpdater : ListUpdater
        lateinit var fetchCompletionHandler : FetchCompletionHandler
        lateinit var  list : List<Person>
        private var TAG : String = Controller::class.simpleName.toString()


        fun executeFetch() {


            list = listOf()

            fetchCompletionHandler = {response, error ->


                if (response != null) {
                    list = response.people
                    listUpdater.listUpdate(list)

                }

            }

            val datasource = DataSource()

            datasource.fetch(null,fetchCompletionHandler)


        }
        fun registerListUpdater(listUpdate: ListUpdater){

            listUpdater = listUpdate

        }

    }




}