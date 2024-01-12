package com.openwallet.ui.activity.fragment.register

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openwallet.R
import com.openwallet.base.BaseDialogFragment
import com.openwallet.databinding.BottomSheetCountryCodeBinding
import com.openwallet.ext.gone
import com.openwallet.ext.parseState
import com.openwallet.ext.visible
import com.openwallet.ui.activity.fragment.register.model.CountryCodeInfo
import com.openwallet.ui.activity.fragment.register.model.CountryCodeListViewModel
import com.openwallet.util.DisplayUtil
import com.openwallet.util.LogUtils


class CountryCodeDialogFragment : BaseDialogFragment() {

    //total country code list
    private val countryCodeSet = mutableListOf<ListItemModel>()
    lateinit var adapter: BaseMobileDesignListAdapter
    private val binding by viewBinding<BottomSheetCountryCodeBinding>()

    private val viewModel by viewModelFragment<CountryCodeListViewModel>()

    override fun initViewAndData(view: View, savedInstanceStat: Bundle?) {

        val ivClose = view.findViewById<ImageView>(R.id.bottomSheetCloseButton)
        ivClose.setOnClickListener{
            dismiss()
        }

        val countryCodeListview: RecyclerView = binding.countryCodeList
        countryCodeListview.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        adapter = ListItemAdapter()
        countryCodeListview.adapter = adapter

        adapter.onListItemClickListener = (object : OnListItemClickListener {
            override fun onListItemClick(position: Int, item: ListItemModel) {
                setFragmentResult(
                    "passCountryCode",
                    bundleOf("countryCode" to "+" + item.trailingDetail)
                )
                this@CountryCodeDialogFragment.dismiss()
            }
        })

        val searchCountryCodeView: SearchView = binding.svCountryCode

        binding.svCountryCode.setOnClickListener {
            searchCountryCodeView.isIconified = false
        }
        binding.errorView.btnRetry.setOnClickListener {
            requestData()
        }

        searchCountryCodeView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { newText ->
                    val filteredList = countryCodeSet.filter {
                        val titleMatch = it.title.contains(newText, true)
                        if (it.trailingDetail == null) titleMatch else titleMatch
                                || it.trailingDetail!!.contains(newText,true)
                    }
                    adapter.updateListItems(filteredList)
                    return true
                }
                return false
            }
        })

        //load data
        requestData()

    }

    override fun requestData() {
        viewModel.getCountryCodeInfo().observe(this) { res ->
            parseState(res, onLoading = {
                showLoading()
            }, onSuccess = {
                binding.loading.gone()
                if (it.data == null || it.data.isEmpty()) {
                    //data empty use local data
                    showError()
                } else {
                    hideStateView()
                    onDataSuccess(it.data)
                }
            }, onError = {
                //api error use local data.
                showError()
            })
        }
    }

    private fun onDataSuccess(codeList: List<CountryCodeInfo>) {
        countryCodeSet.clear()
        for (item in codeList) {
            var modelItem = ListItemModelBuilder(item.country.orEmpty()).apply {
                trailingDetail { item.code.toString() }
            }.build()
            countryCodeSet.add(modelItem)
        }
        adapter.updateListItems(countryCodeSet)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val dlg = Dialog(context!!, R.style.BottomSheetDialogStyle)
        val params = dlg.window!!.attributes
        val height = DisplayUtil.getScreenHeight(context) - DisplayUtil.dip2px(context, 35f)
        params.height = if(height > 0) height else DisplayUtil.dip2px(context, 655f)
        params.gravity = Gravity.BOTTOM
        dlg.window!!.attributes = params as WindowManager.LayoutParams
        return dlg
    }

    private fun showLoading() {
        binding.stateView.visible()
        binding.loading.visible()
        binding.errorView.errorLayoutRoot.gone()
    }

    private fun showError() {
        binding.stateView.visible()
        binding.loading.gone()
        binding.errorView.errorLayoutRoot.visible()
    }

    private fun hideStateView() {
        binding.stateView.gone()
    }

}