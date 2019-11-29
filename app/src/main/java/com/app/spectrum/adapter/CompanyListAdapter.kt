package com.app.spectrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.spectrum.R
import com.app.spectrum.`interface`.BindableAdapter
import com.app.spectrum.databinding.CompanyListItemBinding
import com.app.spectrum.model.CompanyDataModel

/**
 * Created by amresh on 29/11/2019
 */
class CompanyListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BindableAdapter<List<CompanyDataModel>> {

    var companyList = emptyList<CompanyDataModel>()

    override fun setData(data: List<CompanyDataModel>) {
        companyList = data
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
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    inner class CompanyitemViewHolder(var companyListItemBinding: CompanyListItemBinding) :
        RecyclerView.ViewHolder(companyListItemBinding.root) {

        fun BindItem(companyItem: CompanyDataModel) {

        }


    }
}