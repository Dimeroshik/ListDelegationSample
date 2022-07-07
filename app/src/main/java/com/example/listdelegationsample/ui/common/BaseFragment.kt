package com.example.listdelegationsample.ui.common

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import moxy.MvpAppCompatFragment
import com.example.listdelegationsample.extension.alertDialog
import com.example.listdelegationsample.extension.hideSoftKeyboard
import com.example.listdelegationsample.extension.navigateUp
import com.example.listdelegationsample.extension.onShowSnack
import com.example.listdelegationsample.presentation.view.BaseMvpView


abstract class BaseFragment<T : ViewBinding> : MvpAppCompatFragment(), BaseMvpView {

    //Тут хранится наш binding, тут можно увидеть новую для вас T:ViewBinding
    //это называется дженериком и если не углубляться позволяет создавать конструкции
    //с неопределенным классом, в это случае наследующимся от ViewBinding
    //класс который мы подставляем определяется во время компиляции, поэтому мы и
    //передаем его в инициализации фрагмента, как в ListFragment
    var binding: T? = null

//    val progressView: ProgressDialog by lazy { ProgressDialog() }

    var progressShowed: Boolean = false



    abstract fun onViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = onViewBinding(inflater, container).let {
        binding = it
        it.root
    }

    override fun showSnack(resId: Int) {
        onShowSnack(getString(resId))
    }

    override fun showSnack(message: String) {
        onShowSnack(message)
    }

    override fun showProgressView() {
//        if (!progressView.isAdded && !progressShowed){
//            progressView.show(childFragmentManager, "progressView")
//            progressShowed = true
//        }

    }

    override fun hideProgressView() {
//        if (progressShowed){
//            progressView.dismiss()
//            progressShowed = false
//        }
    }

    override fun onNavControllerUp() {
        navigateUp()
    }

    override fun showAlertDialog(
        titleRes: Int?,
        messageRes: Int?,
        positiveRes: Int,
        negativeRes: Int?,
        messageValue: String?,
        onPositive: (() -> Unit)?,
        onNegative: (() -> Unit)?,
        onDismiss: (() -> Unit)?,
        onCancel: (() -> Unit)?
    ) {
        alertDialog(
            titleRes,
            messageRes,
            positiveRes,
            negativeRes,
            messageValue,
            onPositive,
            onNegative,
            onDismiss,
            onCancel
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideProgressView()
        binding = null
    }

    override fun hideKeyBoard() {
        hideSoftKeyboard()
    }


}