package com.app.spectrum.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.spectrum.R
import kotlinx.android.synthetic.main.fragment_company.*

/**
 * Created by amresh on 28/11/2019
 */
class CompanyFragment : Fragment(){

    companion object{
         val TAG = CompanyFragment::class.java.simpleName
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setHasOptionsMenu(true)

    }

    private fun setupRecyclerView() {
        company_list.layoutManager = LinearLayoutManager(this.context)
        company_list.setHasFixedSize(true)
    }
}