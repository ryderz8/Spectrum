package com.app.spectrum.ui

import android.os.Bundle
import android.view.View.OnClickListener
import androidx.appcompat.app.ActionBar
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

        setUpToolbar()

        loadHomeFragment()
    }

    private fun loadHomeFragment(){
        val fragment = CompanyFragment()
        loadFragment(fragment,CompanyFragment.TAG)
        setToolbarTitle("Companies")
        setDisableBackBtn()

    }

     fun loadFragment(fragment: Fragment, tag : String){
       supportFragmentManager.beginTransaction()
           .replace(R.id.container,fragment,tag)
           .addToBackStack(tag)
           .commitAllowingStateLoss()

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        val supportActionBar: ActionBar? = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setHomeButtonEnabled(true)
            supportActionBar.setDisplayShowTitleEnabled(false)
        }
    }

    fun setToolbarTitle(title: String?) {
        if (toolbar != null) {
            toolbar.title = title
             if(title.equals("Companies", true))  {
                toolbar.setTitleMargin(50,0,0,0)
            }else{
                 toolbar.setTitleMargin(0,0,0,0)

             }
        }
    }

    fun setDisableBackBtn() {
        toolbar.navigationIcon = null
    }

    fun setBackToolbar(clickListener: OnClickListener?) {
        toolbar.setNavigationIcon(R.drawable.back)
        toolbar.setNavigationOnClickListener(clickListener)
    }

    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount > 0) {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.container)
            if (fragment is MemberFragment) {
                setDisableBackBtn()
                setToolbarTitle("Companies")
                super.onBackPressed()
            }else{
                finish()
            }
        } else {
            super.onBackPressed()
        }
    }
}