package com.app.spectrum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.spectrum.BR
import com.app.spectrum.R
import com.app.spectrum.databinding.MemberListItemBinding
import com.app.spectrum.interfaces.BindableAdapter
import com.app.spectrum.model.MemberDataModel

/**
 * Created by amresh on 30/11/2019
 */
class MemberListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BindableAdapter<List<MemberDataModel>> {

    var memberList = emptyList<MemberDataModel>()
    override fun setData(data: List<MemberDataModel>) {
        memberList = data
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
        val item = memberList[position]
        item.let {
            (holder as MemberitemViewHolder).BindItem(it)
        }
    }


    override fun getItemCount(): Int {
        return memberList.size
    }

    inner class MemberitemViewHolder(var memberListItemBinding: MemberListItemBinding) :
        RecyclerView.ViewHolder(memberListItemBinding.root) {

        fun BindItem(memberItem: MemberDataModel) {
            memberListItemBinding.setVariable(BR.dataModel, memberItem)
            memberListItemBinding.executePendingBindings()

        }


    }
}