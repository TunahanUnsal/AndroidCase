package com.scorp.case1.view

import android.annotation.SuppressLint
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
import com.scorp.case1.model.FetchError
import com.scorp.case1.viewModel.OnSwipeTouchListener
import kotlin.math.log


class MainActivity : AppCompatActivity(), ListUpdater {

    private lateinit var binding: ActivityMainBinding
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

        binding.nextButton.setOnClickListener {

            Controller.executeFetch(temp_next)
            temp_old = temp_next
            binding.progressBar.visibility = View.VISIBLE

        }

        binding.personList.setOnTouchListener(object : OnSwipeTouchListener(applicationContext) {

            override fun onSwipeBottom() {

                if (temp_next!="null"){

                    Controller.executeFetch(temp_next)
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(TAG, "onSwipeBottom")
                }

            }

        })

        binding.refreshButton.setOnClickListener {

            Controller.executeFetch(null)
            binding.progressBar.visibility = View.VISIBLE
            binding.refreshButton.visibility = View.INVISIBLE

        }


    }

    private fun recyclerViewAdapter(list: List<Person>) {

        binding.personList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.personList.adapter = PersonAdapter(list)

        binding.progressBar.visibility = View.INVISIBLE

    }

    override fun listUpdate(list: List<Person>, next: String,error: String) {

        Log.d(TAG, "listUpdate next -> $next")
        Log.d(TAG, "listUpdate error -> $error")

        val errorCode : Int = Controller.errorCodeWizard(error,next)

        Log.d(TAG, "listUpdate error code -> $errorCode")

        if (errorCode == 0){

            if (next == "null") {
                Log.d(TAG, "listUpdate next -> null")
                binding.emptyText.visibility = View.VISIBLE
                binding.refreshButton.visibility = View.VISIBLE
                binding.nextButton.visibility = View.INVISIBLE
                temp_next = next
                recyclerViewAdapter(list)
            } else {
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