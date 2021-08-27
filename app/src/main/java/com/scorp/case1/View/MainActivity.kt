package com.scorp.case1.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.scorp.case1.Model.Person
import com.scorp.case1.ViewModel.Controller
import com.scorp.case1.ViewModel.ListUpdater
import com.scorp.case1.ViewModel.PersonAdapter
import com.scorp.case1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ListUpdater {

    private lateinit var binding: ActivityMainBinding
    private var TAG : String = MainActivity::class.simpleName.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()

        Controller.executeFetch()  //TODO


    }
    private fun listeners(){
        
        Controller.registerListUpdater(this)


    }
    private fun recyclerViewAdapter(list: List<Person>){

        binding.personList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.personList.adapter = PersonAdapter(list)

    }

    override fun listUpdate(list: List<Person>) {

        recyclerViewAdapter(list)

    }


}