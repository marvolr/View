package com.example.marc9.sound

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marc9.sound.RecyclerViewAdapter
import kotlinx.android.synthetic.main.slide_turismo_main.*
import java.util.ArrayList

class PerfilFragment(): Fragment() {

    private val TAG = "MainActivity"
    //vars
    val mNames = mutableListOf("Linea 1","Linea 2", "Linea 3", "Linea 4", "Linea 4A", "Linea 5", "Linea 6")
    val mImageUrls =  intArrayOf(
            R.drawable.cerclered,
            R.drawable.cerclebackgroundyello,
            R.drawable.cerclemarron,
            R.drawable.cercleazuloscuro,
            R.drawable.cercleazulclaro,
            R.drawable.cerclebackgroundgreen,
            R.drawable.cerclemorado
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_perfil,container,false)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }


    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview")

        val layoutManager = LinearLayoutManager( activity,LinearLayoutManager.HORIZONTAL,false)
        slide_recycler_turismo.setLayoutManager(layoutManager)
        val adapter = RecyclerViewAdapter(activity, mNames, mImageUrls)
        slide_recycler_turismo.setAdapter(adapter)
    }
}