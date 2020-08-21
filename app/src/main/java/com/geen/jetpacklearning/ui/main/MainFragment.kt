package com.geen.jetpacklearning.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geen.jetpacklearning.R
import com.geen.jetpacklearning.bean.UserModel
import com.geen.jetpacklearning.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mBinding: MainFragmentBinding
    private var userModel : UserModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MainFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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
                    userModel?.userName  = "Geen"
                    viewModel.updateModel(userModel)
                    Toast.makeText(activity, "message", Toast.LENGTH_LONG).show()
                }

                mBinding.message1 -> {
                    viewModel.userModel?.userName = "Geen-----000"
                    Toast.makeText(activity, "message1", Toast.LENGTH_LONG).show()
                }


            }

        }
    }
}