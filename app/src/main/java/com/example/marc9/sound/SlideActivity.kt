package com.example.marc9.sound

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.slide_main.*

class SlideActivity(): AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.slide_main)



        val dotsIndicator = dots_indicator
        val viewPager = slideviewpager
        val adapter = SlideAdapter(this)
        viewPager.adapter = adapter
        dotsIndicator.setViewPager(viewPager)

        button_slide.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                if (p0==3){
                    button_slide.visibility= View.VISIBLE
                }else{button_slide.visibility=View.GONE}
            }

        })

    }





}