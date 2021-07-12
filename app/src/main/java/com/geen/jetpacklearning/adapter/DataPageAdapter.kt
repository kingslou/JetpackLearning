package com.geen.jetpacklearning.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geen.jetpacklearning.R
import com.geen.jetpacklearning.databinding.ItemDataBinding
import kotlinx.android.synthetic.main.item_data.view.*


/**
 * @Author LuoJi
 * @Date 2021/7/12-10:37
 * @Desc
 */
class DataPageAdapter : RecyclerView.Adapter<DataPageAdapter.ViewHolder>() {

    private var dataList = arrayListOf<String>()
    private var mBinding :ItemDataBinding? = null

    fun setData(listString: MutableList<String>){
        dataList.clear()
        dataList.addAll(listString)
        notifyDataSetChanged()
    }

    fun addData(listString: MutableList<String>){
        dataList.addAll(listString)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_data,parent,false)
        return ViewHolder(mBinding!!.root)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = dataList[holder.adapterPosition]
        Log.d("下标",holder.adapterPosition.toString())
        holder.itemView.tvName.text = dataItem
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

