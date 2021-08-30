package com.scorp.case1.view.activity

import PaginationScrollListener
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.size
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
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var visibleItemCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Controller.executeFetch(null)

        listeners()

        tempNext = null.toString()
        tempOld = null.toString()


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listeners() {

        Controller.registerListUpdater(this)

        binding.swiperefresh.setOnRefreshListener {

            scrollUp()

        }


        binding.refreshButton.setOnClickListener {

            refresh()

        }

    }

    private fun recyclerViewAdapter(list: List<Person>) {  //list create or update


        people.addAll(list)


        binding.personList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.personList.adapter = PersonAdapter(people)

        binding.progressBar.visibility = View.INVISIBLE
        binding.swiperefresh.isRefreshing = false

        Log.d(TAG, "people.size: " + people.size)
        Log.d(TAG, "list.size: " + list.size)




        binding.personList.addOnScrollListener(object :
            PaginationScrollListener(binding.personList.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                Log.d(TAG, "isLastPage")

                return isLastPage
            }

            override fun isLoading(): Boolean {
                Log.d(TAG, "isLoading")
                return isLoading
            }

            override fun loadMoreItems() {

                if (!isLoading) {
                    Log.d(TAG, "loadMoreItems")
                    scrollDown()
                }

            }
        })


    }

    override fun listUpdate(                //list update interface
        list: List<Person>,
        next: String,
        error: String
    ) {                                        


        Log.d(TAG, "listUpdate next -> $next")
        Log.d(TAG, "listUpdate error -> $error")

        val errorCode: Int = Controller.errorCodeWizard(error, tempOld)

        Log.d(TAG, "listUpdate error code -> $errorCode")

        if (errorCode == 0) {

            if (next == "null" && list.isNotEmpty()) {

                binding.refreshButton.visibility = View.VISIBLE
                recyclerViewAdapter(list)
                isLoading = true


            } else if (next == "null" && list.isEmpty()) {

                binding.emptyText.visibility = View.VISIBLE
                binding.refreshButton.visibility = View.VISIBLE
                recyclerViewAdapter(list)
                isLoading = false
            } else {

                binding.emptyText.visibility = View.INVISIBLE
                binding.refreshButton.visibility = View.INVISIBLE
                tempNext = next
                recyclerViewAdapter(list)
                isLoading = false
            }


        }


    }

    private fun scrollDown() {

        binding.progressBar.visibility = View.VISIBLE
        Controller.executeFetch(tempNext)
        tempOld = tempNext
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true


    }

    private fun scrollUp() {

        binding.progressBar.visibility = View.VISIBLE
        Controller.executeFetch(null)
        people.clear()
        tempNext = null.toString()
        tempOld = null.toString()

    }

    private fun refresh() {

        Controller.executeFetch(null)
        binding.progressBar.visibility = View.VISIBLE
        binding.refreshButton.visibility = View.INVISIBLE

    }


}
