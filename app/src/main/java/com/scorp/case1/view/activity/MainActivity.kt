package com.scorp.case1.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scorp.case1.model.Person
import com.scorp.case1.viewModel.Controller
import com.scorp.case1.viewModel.ListUpdater
import com.scorp.case1.view.adapter.PersonAdapter
import com.scorp.case1.databinding.ActivityMainBinding
import com.scorp.case1.viewModel.OnSwipeTouchListener



class MainActivity : AppCompatActivity(), ListUpdater {

    private lateinit var binding: ActivityMainBinding  //view binding
    private var TAG: String = MainActivity::class.simpleName.toString()
    private lateinit var tempNext: String
    private lateinit var tempOld: String
    private var people: MutableList<Person> = mutableListOf()
    private var tuna : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()

        Controller.executeFetch(null)

        tempNext = null.toString()
        tempOld = null.toString()





    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listeners() {

        Controller.registerListUpdater(this)


        binding.personList.setOnTouchListener(object : OnSwipeTouchListener(applicationContext) {  //pull to refresh

            override fun onSwipeBottom() {



            }


        })

        binding.personList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && recyclerView.scrollState==1) {
                    binding.progressBar.visibility = View.VISIBLE
                    tuna = recyclerView.scrollState
                    Controller.executeFetch(tempNext)
                    tempOld = tempNext
                    binding.progressBar.visibility = View.VISIBLE
                }
                if (!recyclerView.canScrollVertically(-1)&&recyclerView.scrollState==0) {

                    binding.progressBar.visibility = View.VISIBLE
                    Controller.executeFetch(null)
                    tempNext = null.toString()
                    tempOld = null.toString()

                }

            }
        })

        binding.refreshButton.setOnClickListener {  //go to first page


            Controller.executeFetch(null)
            binding.progressBar.visibility = View.VISIBLE
            binding.refreshButton.visibility = View.INVISIBLE


        }


    }

    private fun recyclerViewAdapter(list: List<Person>) {  //list create or update


        people.addAll(list)

        binding.personList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.personList.adapter = PersonAdapter(people)

        binding.progressBar.visibility = View.INVISIBLE

        binding.personList.scrollToPosition(people.size)


    }

    override fun listUpdate(list: List<Person>, next: String,error: String) { //list update interface

        Log.d(TAG, "listUpdate next -> $next")
        Log.d(TAG, "listUpdate error -> $error")

        val errorCode : Int = Controller.errorCodeWizard(error,tempOld)

        Log.d(TAG, "listUpdate error code -> $errorCode")

        if (errorCode == 0){

            if(next == "null"&& list.isNotEmpty()){

                binding.refreshButton.visibility = View.VISIBLE
                recyclerViewAdapter(list)
            }
            else if (next == "null" && list.isEmpty()){

                binding.emptyText.visibility = View.VISIBLE
                binding.refreshButton.visibility = View.VISIBLE

                recyclerViewAdapter(list)
            }

            else {

                Log.d(TAG, "listUpdate next -> !null ")
                binding.emptyText.visibility = View.INVISIBLE
                binding.refreshButton.visibility = View.INVISIBLE
                tempNext = next
                recyclerViewAdapter(list)

            }


        }

    }


}
