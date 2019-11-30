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
import com.app.spectrum.adapter.CompanyListAdapter
import com.app.spectrum.databinding.FragmentCompanyBinding
import com.app.spectrum.interfaces.onItemClick
import com.app.spectrum.model.CompanyDataModel
import com.app.spectrum.remote.Injection
import com.app.spectrum.viewmodel.CommonViewModel
import com.app.spectrum.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_company.*
import kotlinx.android.synthetic.main.layout_error.*

/**
 * Created by amresh on 28/11/2019
 */
class CompanyFragment : Fragment() {

    private lateinit var viewModel: CommonViewModel
    private lateinit var companyListadapter: CompanyListAdapter

    private lateinit var binding: FragmentCompanyBinding


    companion object {
        val TAG = CompanyFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentCompanyBinding>(
            inflater,
            R.layout.fragment_company,
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
        viewModel.companyData.observe(this, renderCompanyData)

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
        binding.lifecycleOwner = this
    }

    private fun setupRecyclerView() {
        activity?.let {
            company_list.layoutManager = LinearLayoutManager(this.context)
            companyListadapter = CompanyListAdapter(onItemClick)
            company_list.setHasFixedSize(true)
            company_list.adapter = companyListadapter
            company_list.addItemDecoration(
                DividerItemDecoration(
                    it,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private var onItemClick = object : onItemClick {
        override fun onClick(companyDataModel: CompanyDataModel) {
            activity?.let {
                val fragment = MemberFragment()
                (it as HomeActivity).loadFragment(fragment, MemberFragment.TAG)
            }
            viewModel.loadMemberData(companyDataModel)
        }
    }

    //observers
    private val renderCompanyData = Observer<List<CompanyDataModel>> {
        Log.v(TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCompanyData()
    }
}