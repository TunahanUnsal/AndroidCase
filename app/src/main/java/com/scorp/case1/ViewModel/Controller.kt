package com.scorp.case1.ViewModel

import android.util.Log
import com.scorp.case1.Model.DataSource
import com.scorp.case1.Model.FetchCompletionHandler

import com.scorp.case1.Model.Person

 class Controller {


     companion object {


         lateinit var fetchCompletionHandler : FetchCompletionHandler

         fun getPeople(): List<Person> {

             val people = listOf<Person>(
                 Person(1, "Tuna Ünsal"),
                 Person(2, "Tunaa Ünsal"),
                 Person(3, "Tunaaaa Ünsal"),
                 Person(4, "Tunaaaaaa Ünsal"),
                 Person(5, "Tunaaaaa Ünsal"),
                 Person(6, "Tunadsd Ünsal"),
                 Person(7, "Tunfsdfa Ünsal"),
                 Person(8, "Tunasdaa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),
                 Person(9, "Tundasdfdsa Ünsal"),

                 )
             return people

         }
         fun executeFetch(){


             val fetchCompletionHandler: FetchCompletionHandler = {response, error ->

                 Log.d("TAG", response.toString())
                 Log.d("TAG", error.toString())

             }

             val datasource = DataSource()

             datasource.fetch(null,fetchCompletionHandler)



         }

     }



}