package com.app.spectrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.spectrum.BR
import com.app.spectrum.R
import com.app.spectrum.databinding.MemberListItemBinding
import com.app.spectrum.interfaces.BindableAdapter
import com.app.spectrum.interfaces.OnMemberClick
import com.app.spectrum.model.ClickEnum
import com.app.spectrum.model.MemberDataModel

/**
 * Created by amresh on 30/11/2019
 */
class MemberListAdapter(val mOnItemClick: OnMemberClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BindableAdapter<List<MemberDataModel>>, Filterable {

    var memberList = emptyList<MemberDataModel>()
    var filteredMemberList = emptyList<MemberDataModel>()

    override fun setData(data: List<MemberDataModel>) {
        memberList = data
        filteredMemberList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MemberitemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.member_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredMemberList[position]
        item.let {
            (holder as MemberitemViewHolder).BindItem(it)
        }
    }


    override fun getItemCount(): Int {
        return filteredMemberList.size
    }

    inner class MemberitemViewHolder(var memberListItemBinding: MemberListItemBinding) :
        RecyclerView.ViewHolder(memberListItemBinding.root) {

        fun BindItem(memberItem: MemberDataModel) {
            memberListItemBinding.setVariable(BR.dataModel, memberItem)
            memberListItemBinding.executePendingBindings()

            memberListItemBinding.followBtn.setOnClickListener{
                mOnItemClick.onMemberClick(memberItem, ClickEnum.FOLLOW_CLICK)
            }

            memberListItemBinding.favIv.setOnClickListener{
                mOnItemClick.onMemberClick(memberItem, ClickEnum.FAV_CLICK)

            }


        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredMemberList = results.values as List<MemberDataModel>
                (this@MemberListAdapter).notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredResults: List<MemberDataModel> = if (constraint.isEmpty()) {
                    memberList
                } else {
                    getFilteredResults(constraint.toString().toLowerCase())
                }
                filteredMemberList = ArrayList()
                filteredMemberList = filteredResults
                val results = FilterResults()
                results.values = filteredMemberList
                return results
            }
        }
    }


    fun getFilteredResults(constraint: String): List<MemberDataModel> {
        var results = emptyList<MemberDataModel>().toMutableList()

        for (item in memberList) {
            if (item.name.first.toLowerCase().contains(constraint) || item.name.last.toLowerCase().contains(constraint)) {
                results.add(item)
            }
        }
        return results
    }
}