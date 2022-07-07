package com.example.listdelegationsample.presentation.presenter

import androidx.annotation.StringRes
import com.example.listdelegationsample.R
import com.example.listdelegationsample.common.event.EventBus
import com.example.listdelegationsample.presentation.view.BaseMvpView
import com.example.listdelegationsample.entity.network.base.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import moxy.MvpPresenter
import com.example.listdelegationsample.extension.withMain
import kotlin.coroutines.CoroutineContext

//Наш базовый класс presenter, Конструкция View была объяснена в BaseFragment
abstract class BaseMvpPresenter<View : BaseMvpView> : MvpPresenter<View>() {

    //Это тема асинхронности которую затронем далее
    private val job = Job()
    private val coroutineContext: CoroutineContext = Dispatchers.IO + job

    val scope = CoroutineScope(coroutineContext)

    suspend fun showSnack(@StringRes resId: Int) {
        withMain {
            viewState.showSnack(resId)
        }
    }

    suspend fun showSnack(message: String?) {
        message?.let {
            withMain {
                viewState.showSnack(it)
            }
        }
    }

    suspend fun showProgressView() {
        withMain {
            viewState?.showProgressView()
        }
    }

    suspend fun hideProgressView() {
        withMain {
            viewState?.hideProgressView()
        }
    }

    inline fun <T> register(eventClass: Class<T>, crossinline f: (T) -> Unit) {
        EventBus.register(this.javaClass.simpleName, Dispatchers.Main, eventClass) {
            f.invoke(it)
        }
    }

    inline fun <T> register(contextName: String, eventClass: Class<T>, crossinline f: (T) -> Unit) {
        EventBus.register(contextName, Dispatchers.Main, eventClass) {
            f.invoke(it)
        }
    }

    fun postEvent(event: Any) {
        EventBus.post(event)
    }

    fun unregister() {
        EventBus.unregister(this.javaClass.simpleName)
    }

    fun <T> unregister(eventClass: Class<T>) {
        EventBus.unregister(this.javaClass.simpleName, eventClass)
    }

    open suspend fun handleServerError(result: Result<Any>) {
        withMain {
            when (result) {
                is Result.InternetConnectionError -> {
                    onInternetConnectionError()
                }
            }
        }
    }

    open fun onInternetConnectionError() {
        viewState?.showSnack(R.string.internet_connection_error)
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}