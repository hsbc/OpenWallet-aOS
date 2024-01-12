package com.openwallet.ui.activity.fragment.startregister

import android.os.Bundle
import android.view.View
import com.openwallet.R
import com.openwallet.base.BaseFragment
import com.openwallet.databinding.FragmentRegisterStartBinding
import com.openwallet.ext.nav
import com.openwallet.util.CommonUtils


class StartRegisterFragment : BaseFragment() {

    private val binding by viewBinding<FragmentRegisterStartBinding>()

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {

        binding.toolbar.init(
            isShowBack = true,
            onBackClickListener = {
                nav().navigateUp()
                CommonUtils.clearInput()
            }
        )

        binding.btnRegister.setOnClickListener {
            nav().navigate(R.id.action_StartRegisterFragment_to_RegisterNameFragment)
        }
    }

}