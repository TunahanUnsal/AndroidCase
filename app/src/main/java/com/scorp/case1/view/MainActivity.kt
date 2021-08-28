package com.scorp.case1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scorp.case1.model.Person
import com.scorp.case1.viewModel.Controller
import com.scorp.case1.viewModel.ListUpdater
import com.scorp.case1.viewModel.PersonAdapter
import com.scorp.case1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ListUpdater {

    private lateinit var binding: ActivityMainBinding
    private var TAG : String = MainActivity::class.simpleName.toString()
    private lateinit var temp_next : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()

        Controller.executeFetch(null)  //TODO


    }
    private fun listeners(){
        
        Controller.registerListUpdater(this)

        binding.nextButton.setOnClickListener {

            Controller.executeFetch(temp_next)

        }


    }
    private fun recyclerViewAdapter(list: List<Person>){

        binding.personList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.personList.adapter = PersonAdapter(list)

    }

    override fun listUpdate(list: List<Person>,next : String) {

        Log.d(TAG, "listUpdate $next")

        if (next=="null"){

            binding.emptyText.visibility = View.VISIBLE
            recyclerViewAdapter(list)
        }
        else{

            temp_next = next
            recyclerViewAdapter(list)
        }
        





    }


}