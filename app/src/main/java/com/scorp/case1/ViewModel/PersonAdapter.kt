package com.scorp.case1.ViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scorp.case1.Model.Person
import com.scorp.case1.R
import com.scorp.case1.databinding.ActivityMainBinding
import com.scorp.case1.databinding.PersonListItemBinding

class PersonAdapter(private val dataSet: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    private lateinit var binding: PersonListItemBinding

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewId: TextView = view.findViewById(R.id.id)

        val  textViewName : TextView = view.findViewById(R.id.name)

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.person_list_item, viewGroup, false)



        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        viewHolder.textViewId.text = " ("+dataSet[position].id.toString()+") "
        viewHolder.textViewName.text = dataSet[position].fullName
    }

    override fun getItemCount() = dataSet.size

}