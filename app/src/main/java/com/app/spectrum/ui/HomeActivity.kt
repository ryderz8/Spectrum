package com.app.spectrum.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.spectrum.R
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by amresh on 26/11/2019
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadHomeFragment()
    }

    private fun loadHomeFragment(){
        val fragment = CompanyFragment()
        loadFragment(fragment,CompanyFragment.TAG)
        setSupportActionBar(toolbar)

    }

     fun loadFragment(fragment: Fragment, tag : String){
       supportFragmentManager.beginTransaction()
           .replace(R.id.container,fragment,tag)
           .addToBackStack(tag)
           .commitAllowingStateLoss()

    }
}