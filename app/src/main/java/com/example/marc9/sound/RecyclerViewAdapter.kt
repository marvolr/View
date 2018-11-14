package com.example.marc9.sound

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import kotlinx.android.synthetic.main.layout_slide_turismo.view.*
import java.util.*


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    //vars

    private val mNames: MutableList<String>
    private val mImageUrls: IntArray
    private var mContext: Context? = null


    constructor(activity: FragmentActivity?, mNames: MutableList<String>, mImageUrls: IntArray){
        this.mNames = mNames
        this.mImageUrls = mImageUrls
        mContext = activity
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.layout_slide_turismo, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")

        holder.bind(mNames[position],mImageUrls[position])

      // holder.mImageUrls(mImageUrls[position].setOnClickListener {
      //      Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position))
      //      Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show()
      //  })
    }

    override fun getItemCount(): Int {
        return mImageUrls.size
    }

    companion object {

        private val TAG = "RecyclerViewAdapter"
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(s: String, n: Int) {
            itemView.image_view.setImageResource(n)
            itemView.name.text= s


        }

    }
}
