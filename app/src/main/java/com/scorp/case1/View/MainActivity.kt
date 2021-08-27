package com.scorp.case1.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.scorp.case1.ViewModel.Controller
import com.scorp.case1.ViewModel.Controller.Companion.getPeople
import com.scorp.case1.ViewModel.PersonAdapter
import com.scorp.case1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var TAG : String = MainActivity::class.simpleName.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()
        recyclerViewAdapter()

        Controller.executeFetch()


    }
    private fun listeners(){

    }
    private fun recyclerViewAdapter(){

        binding.personList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.personList.adapter = PersonAdapter(getPeople())

        if(getPeople().isEmpty()){   //TODO


            Log.d(TAG, "recyclerViewAdapter: asdasdasd"+binding.personList.size)
            binding.emptyText.visibility = View.VISIBLE


        }


    }

}