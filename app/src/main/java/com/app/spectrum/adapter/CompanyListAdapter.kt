package com.app.spectrum.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.spectrum.BR
import com.app.spectrum.R
import com.app.spectrum.databinding.CompanyListItemBinding
import com.app.spectrum.interfaces.BindableAdapter
import com.app.spectrum.model.CompanyDataModel

/**
 * Created by amresh on 29/11/2019
 */
class CompanyListAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
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
        val item = companyList[position]
        item.let {
            (holder as CompanyitemViewHolder).BindItem(it)
        }
    }


    override fun getItemCount(): Int {
        return companyList.size
    }

    inner class CompanyitemViewHolder(var companyListItemBinding: CompanyListItemBinding) :
        RecyclerView.ViewHolder(companyListItemBinding.root) {

        fun BindItem(companyItem: CompanyDataModel) {
            companyListItemBinding.setVariable(BR.dataModel, companyItem)
            companyListItemBinding.executePendingBindings()
        }


    }
}