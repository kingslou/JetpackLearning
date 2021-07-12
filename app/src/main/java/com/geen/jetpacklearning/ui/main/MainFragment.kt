package com.geen.jetpacklearning.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.geen.jetpacklearning.adapter.DataPageAdapter
import com.geen.jetpacklearning.bean.UserModel
import com.geen.jetpacklearning.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var mBinding: MainFragmentBinding
    private var userModel : UserModel? = null

    //使用 androidx.fragment:fragment-ktx:1.2.5 扩展
    private val viewModel:MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MainFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getCurrentName()?.observe(viewLifecycleOwner, Observer<String> {
            Log.e("返回结果", it)
            message.text = it
        })
        viewModel.getUserModel()?.observe(viewLifecycleOwner, Observer {
            Log.e("返回结果", it.userName)
            userModel = it

        })

        mBinding.mainViewModel = viewModel

        mBinding.setClickListener {
            when (it) {
                mBinding.message -> {
                    userModel?.userName = "Geen"
                    viewModel.updateModel(userModel)
                    Toast.makeText(activity, "message", Toast.LENGTH_LONG).show()
                }

                mBinding.message1 -> {
                    viewModel.userModel?.userName = "Geen-----000"
                    Toast.makeText(activity, "message1", Toast.LENGTH_LONG).show()
                }


            }

        }

        initAdapter()

        mBinding.mRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val range: Int = mBinding.mRecycleView.computeHorizontalScrollRange()
                //滑块的偏移量
                val offset: Int = mBinding.mRecycleView.computeHorizontalScrollOffset()
                //可视区域长度
                val extent: Int = mBinding.mRecycleView.computeHorizontalScrollExtent()
                //滑出部分在剩余范围的比例
                val proportion = (offset * 1.0 / (range - extent))
                //计算滚动条宽度
                val transMaxRange: Float = mBinding.progressParent.width.toFloat() - mBinding.viewProgress.width.toFloat()
                //设置滚动条移动
                mBinding.viewProgress.translationX = transMaxRange * proportion.toFloat()
            }
        })
    }

    private fun initAdapter(){

        val mAdapter = DataPageAdapter()

        val dataList = arrayListOf<String>()

        for(index in 1..10){
            dataList.add(index.toString())
        }
        mAdapter.setData(dataList)
        mBinding.mRecycleView.adapter = mAdapter


    }
}