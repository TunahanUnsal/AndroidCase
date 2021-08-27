package com.scorp.case1.ViewModel

import android.util.Log
import com.scorp.case1.Model.DataSource
import com.scorp.case1.Model.FetchCompletionHandler

import com.scorp.case1.Model.Person
import com.scorp.case1.View.MainActivity
import com.scorp.case1.databinding.ActivityMainBinding

class Controller {



    companion object {

        lateinit var listUpdater : ListUpdater
        lateinit var fetchCompletionHandler : FetchCompletionHandler
        lateinit var  list : List<Person>


        fun executeFetch() {


            list = listOf<Person>()



            val fetchCompletionHandler: FetchCompletionHandler = {response, error ->


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