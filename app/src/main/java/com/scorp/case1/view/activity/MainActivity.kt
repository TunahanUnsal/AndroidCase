package com.scorp.case1.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scorp.case1.model.Person
import com.scorp.case1.viewModel.Controller
import com.scorp.case1.viewModel.ListUpdater
import com.scorp.case1.view.adapter.PersonAdapter
import com.scorp.case1.databinding.ActivityMainBinding
import com.scorp.case1.viewModel.OnSwipeTouchListener



class MainActivity : AppCompatActivity(), ListUpdater {

    private lateinit var binding: ActivityMainBinding  //view binding
    private var TAG: String = MainActivity::class.simpleName.toString()
    private lateinit var temp_next: String
    private lateinit var temp_old: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()

        Controller.executeFetch(null)
        temp_old = null.toString()


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listeners() {

        Controller.registerListUpdater(this)

        binding.nextButton.setOnClickListener {   //next page

            Controller.executeFetch(temp_next)
            temp_old = temp_next
            binding.progressBar.visibility = View.VISIBLE
            binding.nextButton.visibility = View.INVISIBLE

        }

        binding.personList.setOnTouchListener(object : OnSwipeTouchListener(applicationContext) {  //pull to refresh

            override fun onSwipeBottom() {

                if (temp_old!="null"){

                    Controller.executeFetch(temp_old)
                    binding.progressBar.visibility = View.VISIBLE
                    binding.nextButton.visibility = View.INVISIBLE
                    Log.d(TAG, "onSwipeBottom")
                }

            }

        })

        binding.refreshButton.setOnClickListener {  //go to first page


            Controller.executeFetch(null)
            binding.progressBar.visibility = View.VISIBLE
            binding.refreshButton.visibility = View.INVISIBLE
            binding.nextButton.visibility = View.INVISIBLE


        }


    }

    private fun recyclerViewAdapter(list: List<Person>) {  //list create or update

        binding.personList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.personList.adapter = PersonAdapter(list)

        binding.progressBar.visibility = View.INVISIBLE


    }

    override fun listUpdate(list: List<Person>, next: String,error: String) { //list update interface

        Log.d(TAG, "listUpdate next -> $next")
        Log.d(TAG, "listUpdate error -> $error")

        val errorCode : Int = Controller.errorCodeWizard(error,temp_old)

        Log.d(TAG, "listUpdate error code -> $errorCode")

        if (errorCode == 0){


            if(next == "null"&& list.isNotEmpty()){

                //binding.emptyText.visibility = View.VISIBLE
                binding.refreshButton.visibility = View.VISIBLE
                binding.nextButton.visibility = View.INVISIBLE
                recyclerViewAdapter(list)
            }
            else if (next == "null" && list.isEmpty()){

                binding.emptyText.visibility = View.VISIBLE
                binding.refreshButton.visibility = View.VISIBLE
                binding.nextButton.visibility = View.INVISIBLE
                recyclerViewAdapter(list)
            }

            else {
                Log.d(TAG, "listUpdate next -> !null ")
                binding.emptyText.visibility = View.INVISIBLE
                binding.refreshButton.visibility = View.INVISIBLE
                binding.nextButton.visibility = View.VISIBLE
                temp_next = next
                recyclerViewAdapter(list)
            }


        }

    }


}