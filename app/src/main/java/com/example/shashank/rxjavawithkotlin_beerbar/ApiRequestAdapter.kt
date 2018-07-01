package com.example.shashank.rxjavawithkotlin_beerbar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.beer_card_view.view.*


class DataAdapter (private val dataList : ArrayList<ApiModel>, private val listener : Listener) : RecyclerView.Adapter<DataAdapter.ViewHolder>()  {

    private var mArrayList: ArrayList<ApiModel>? = null
    private var mFilteredList: ArrayList<ApiModel>? = null

    fun DataAdapter(arrayList: ArrayList<ApiModel>){
        mArrayList = arrayList
        mFilteredList = arrayList
    }

    interface Listener {

        fun onItemClick(beer : ApiModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(dataList[position], listener, position)
    }

    override fun getItemCount(): Int = dataList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.beer_card_view, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(beer: ApiModel, listener: Listener, position: Int) {

            itemView.name.text = beer.name
            itemView.style.text = beer.style
            itemView.content.text = beer.abv
            itemView.addbeer.setOnClickListener{ listener.onItemClick(beer) }
        }
    }

//    override fun getFilter(): android.widget.Filter {
//        return object : Filter() {
//            protected override fun performFiltering(charSequence: CharSequence): FilterResults {
//
//                val charString = charSequence.toString()
//
//                if (charString.isEmpty()) {
//
//                    mFilteredList = mArrayList
//                } else {
//
//                    val filteredList = ArrayList()
//
//                    for (ApiModel in mArrayList) {
//
//                        if (ApiModel.getApi().toLowerCase().contains(charString) || ApiModel.getName().toLowerCase().contains(charString) || androidVersion.getVer().toLowerCase().contains(charString)) {
//
//                            filteredList.add(ApiModel)
//                        }
//                    }
//
//                    mFilteredList = filteredList
//                }
//
//                val filterResults = FilterResults()
//                filterResults.values = mFilteredList
//                return filterResults
//            }
//
//            protected fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                mFilteredList = filterResults.values
//                notifyDataSetChanged()
//            }
//        }
//    }
}