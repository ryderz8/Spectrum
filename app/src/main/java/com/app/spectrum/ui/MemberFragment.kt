package com.app.spectrum.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.spectrum.BR
import com.app.spectrum.R
import com.app.spectrum.adapter.MemberListAdapter
import com.app.spectrum.databinding.FragmentMemberBinding
import com.app.spectrum.model.CompanyDataModel
import com.app.spectrum.model.MemberDataModel
import com.app.spectrum.remote.Injection
import com.app.spectrum.viewmodel.CommonViewModel
import com.app.spectrum.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_company.*
import kotlinx.android.synthetic.main.fragment_company.layoutEmpty
import kotlinx.android.synthetic.main.fragment_company.layoutError
import kotlinx.android.synthetic.main.fragment_company.progressBar
import kotlinx.android.synthetic.main.fragment_member.*
import kotlinx.android.synthetic.main.layout_error.*

/**
 * Created by amresh on 30/11/2019
 */
class MemberFragment : Fragment(){

    private lateinit var viewModel: CommonViewModel
    private lateinit var memberListadapter: MemberListAdapter

    private lateinit var binding: FragmentMemberBinding

    companion object {
        val TAG = MemberFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentMemberBinding>(
            inflater,
            R.layout.fragment_member,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()

        setupRecyclerView()

        setHasOptionsMenu(true)

    }
    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(
            activity!!,
            ViewModelFactory(Injection.providerRepository())
        ).get(CommonViewModel::class.java)

        binding.setVariable(BR.viewModel, viewModel)
        viewModel.memberData.observe(this, renderMemberData)

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
        binding.lifecycleOwner = this
    }

    private fun setupRecyclerView() {
        activity?.let {
            member_list.layoutManager = LinearLayoutManager(this.context)
            memberListadapter = MemberListAdapter()
            member_list.setHasFixedSize(true)
            member_list.adapter = memberListadapter
            member_list.addItemDecoration(
                DividerItemDecoration(
                    it,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    //observers
    private val renderMemberData = Observer<List<MemberDataModel>> {
        Log.v(TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(CompanyFragment.TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(CompanyFragment.TAG, "onMessageError $it")
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(CompanyFragment.TAG, "emptyListObserver $it")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

}