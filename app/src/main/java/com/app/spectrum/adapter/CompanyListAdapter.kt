package com.app.spectrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.spectrum.BR
import com.app.spectrum.R
import com.app.spectrum.databinding.CompanyListItemBinding
import com.app.spectrum.interfaces.BindableAdapter
import com.app.spectrum.interfaces.onItemClick
import com.app.spectrum.model.ClickEnum
import com.app.spectrum.model.CompanyDataModel

/**
 * Created by amresh on 29/11/2019
 */
 class CompanyListAdapter(val mOnItemClick: onItemClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BindableAdapter<List<CompanyDataModel>>, Filterable {

    var companyList = emptyList<CompanyDataModel>()
    var companyFilteredList = emptyList<CompanyDataModel>()

    override fun setData(data: List<CompanyDataModel>) {
        companyList = data
        companyFilteredList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CompanyitemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.company_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = companyFilteredList[position]
        item.let {
            (holder as CompanyitemViewHolder).BindItem(it)
        }
    }

    override fun getItemCount(): Int {
        return companyFilteredList.size
    }

    inner class CompanyitemViewHolder(var companyListItemBinding: CompanyListItemBinding) :
        RecyclerView.ViewHolder(companyListItemBinding.root) {

        fun BindItem(companyItem: CompanyDataModel) {
            companyListItemBinding.setVariable(BR.dataModel, companyItem)
            companyListItemBinding.executePendingBindings()

            companyListItemBinding.root.setOnClickListener {
                mOnItemClick.onClick(companyItem,ClickEnum.NORMAL_CLICK)
            }

            companyListItemBinding.followBtn.setOnClickListener{
                mOnItemClick.onClick(companyItem,ClickEnum.FOLLOW_CLICK)
            }

            companyListItemBinding.favIv.setOnClickListener{
                mOnItemClick.onClick(companyItem,ClickEnum.FAV_CLICK)

            }
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                companyFilteredList = results.values as List<CompanyDataModel>
                (this@CompanyListAdapter).notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredResults: List<CompanyDataModel> = if (constraint.isEmpty()) {
                    companyList
                } else {
                    getFilteredResults(constraint.toString().toLowerCase())
                }
                companyFilteredList = ArrayList()
                companyFilteredList = filteredResults
                val results = FilterResults()
                results.values = companyFilteredList
                return results
            }
        }
    }


    fun getFilteredResults(constraint: String): List<CompanyDataModel> {
        var results = emptyList<CompanyDataModel>().toMutableList()

        for (item in companyList) {
            if (item.company.toLowerCase().contains(constraint)) {
                results.add(item)
            }
        }
        return results
    }

}