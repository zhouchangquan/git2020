package com.magefitness.app.ui.ftplevel

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.magefitness.app.MainActivity
import com.magefitness.app.MainActivity.Companion.INDEX
import com.magefitness.app.QnrAbilityActivity
import com.magefitness.app.R
import com.magefitness.app.databinding.FtpLevelFragmentBinding
import com.magefitness.repo.foundation.ui.BaseFragment
import com.magefitness.repo.utils.IntentConstant

class FtpLevelFragment : BaseFragment<FtpLevelViewModel, FtpLevelFragmentBinding>() {

    companion object {
        fun newInstance(isFirst: Boolean): FtpLevelFragment {
            return FtpLevelFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IntentConstant.IS_FIRST, isFirst)
                }
            }
        }
    }

    override fun layoutResource() = R.layout.ftp_level_fragment
    override fun viewModelClass() = FtpLevelViewModel::class.java

    private var isFirst = false
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            isFirst = it.getBoolean(IntentConstant.IS_FIRST, false)

        }

        initView()
        viewModel.userFtpLevel()
        viewModel.ftpLevelLiveData.observe(viewLifecycleOwner, Observer {
            var drawable = ContextCompat.getDrawable(context!!, R.drawable.riding_ability_1)
            when (it) {
                0 -> drawable = ContextCompat.getDrawable(context!!, R.drawable.riding_ability_1)
                1 -> drawable = ContextCompat.getDrawable(context!!, R.drawable.riding_ability_2)
                2 -> drawable = ContextCompat.getDrawable(context!!, R.drawable.riding_ability_3)
                3 -> drawable = ContextCompat.getDrawable(context!!, R.drawable.riding_ability_4)
                4 -> drawable = ContextCompat.getDrawable(context!!, R.drawable.riding_ability_5)
            }
            dataBinding.imgLevel.setImageDrawable(drawable)
            dataBinding.ftpLevelShort.text = resources.getStringArray(R.array.ftp_level_short)[it]

            viewModel.ftpLiveData.value?.apply {
                dataBinding.txtThreshold.text = getString(R.string.ftp_threshold_desc).format(this)
                dataBinding.txtLevelValue.text = getString(R.string.ftp_threshold_value).format(this)
            }
        })
    }

    private fun initView() {
        dataBinding.btnRetry.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        dataBinding.btnRetry.setOnClickListener {
            startActivity(QnrAbilityActivity::class.java)
            activity!!.finish()
        }
        if (isFirst) {
            dataBinding.btnRetry.visibility = View.INVISIBLE
        } else {
            dataBinding.btnRetry.visibility = View.VISIBLE
        }
        dataBinding.btnNext.setOnClickListener {
            nextFragment(null)
        }
        dataBinding.imgClose.setOnClickListener {
            if (isFirst) {
                MainActivity.startMainActivity(activity!!, INDEX)
            } else {
                activity!!.finish()
            }

        }

    }
}
