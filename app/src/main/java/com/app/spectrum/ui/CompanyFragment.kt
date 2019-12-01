package com.app.spectrum.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import com.app.spectrum.model.ClickEnum
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
    private var isStartedFromBackstack = true

    private lateinit var binding: FragmentCompanyBinding

    var sortedInAscendingOrder = false

    companion object {
        val TAG = CompanyFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isStartedFromBackstack = false
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

        setupUI()

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

    private fun setupUI() {
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

        sort.setOnClickListener(onFabButtonClicked)
    }

    private var onItemClick = object : onItemClick {
        override fun onClick(companyDataModel: CompanyDataModel, clickType: ClickEnum) {
            when (clickType.title) {
                ClickEnum.NORMAL_CLICK.title -> {
                    activity?.let {
                        val fragment = MemberFragment()
                        (it as HomeActivity).loadFragment(fragment, MemberFragment.TAG)
                        it.setToolbarTitle("Members")
                    }
                    viewModel.loadMemberData(companyDataModel)
                }
                ClickEnum.FOLLOW_CLICK.title -> {
                    if(!companyDataModel.followed) {
                        Toast.makeText(getActivity(), "Followed", Toast.LENGTH_SHORT).show()
                        companyDataModel.followed = true
                        companyListadapter.notifyDataSetChanged()
                    }else{
                        Toast.makeText(getActivity(), "UnFollowed", Toast.LENGTH_SHORT).show()
                        companyDataModel.followed = false
                        companyListadapter.notifyDataSetChanged()
                    }

                }
                ClickEnum.FAV_CLICK.title -> {
                    if(!companyDataModel.fav) {
                        Toast.makeText(activity, "Added to favorites", Toast.LENGTH_SHORT).show()
                        companyDataModel.fav = true
                        companyListadapter.notifyDataSetChanged()
                    }else {
                        Toast.makeText(activity, "Removed from favorites", Toast.LENGTH_SHORT)
                            .show()
                        companyDataModel.fav = false
                        companyListadapter.notifyDataSetChanged()
                    }
                }
            }
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
        if (!isStartedFromBackstack)
            viewModel.loadCompanyData()
    }


    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        menu.clear()
        inflater.inflate(R.menu.search_view_menu, menu)
        val search: MenuItem = menu.findItem(R.id.search)
        val searchView: SearchView = search.actionView as SearchView
        search(searchView)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                companyListadapter.filter.filter(newText)
                return true
            }
        })
    }

    private var onFabButtonClicked: View.OnClickListener = View.OnClickListener {
        if (!companyListadapter.companyFilteredList.isNullOrEmpty()) {
            if (sortedInAscendingOrder) {
                sortedInAscendingOrder = false
                companyListadapter.companyFilteredList =
                    companyListadapter.companyFilteredList.toMutableList().asReversed()
                companyListadapter.notifyDataSetChanged()
            } else {
                sortedInAscendingOrder = true
                companyListadapter.companyFilteredList =
                    companyListadapter.companyFilteredList.toMutableList()
                        .sortedWith(compareBy { it.company })
                companyListadapter.notifyDataSetChanged()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        isStartedFromBackstack = true
    }
}