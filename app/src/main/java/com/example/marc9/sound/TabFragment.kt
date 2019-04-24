package com.example.marc9.sound


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment


class TabFragment : Fragment() {
    internal lateinit var view: View
    internal lateinit var viewPager: ViewPager
    internal lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.sample, container, false)
        viewPager=view.findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.adapter=Myadapter(childFragmentManager)

        tabLayout=view.findViewById<View>(R.id.tabs) as TabLayout

        tabLayout.post{(tabLayout.setupWithViewPager(viewPager))}
        return view
    }

    internal inner class Myadapter(fm:FragmentManager):FragmentPagerAdapter(fm){
        override fun getItem(position:Int): Fragment? {

            when(position){
                0->return SonidoFragment()
                1->return SonidoFragment()
                2->return MiEventoFragment()
            }
            return null

        }

        override fun getCount(): Int {
            return intitems
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when(position){
                0->return "SONIDO"
                1->return "EVENTO"
                2->return "MI EVENTO"
            }
            return null
        }


    }

    companion object{
        lateinit var tabLayout: TabLayout
        lateinit var viewPager: ViewPager
        var intitems= 3
    }


}
