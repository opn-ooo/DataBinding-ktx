@file:JvmName("ActivityDataBinding")

package com.wada811.databinding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity

fun <T : ViewDataBinding> FragmentActivity.dataBinding(): Lazy<T> = object : Lazy<T> {
    private var binding: T? = null
    override fun isInitialized(): Boolean = binding != null
    override val value: T
        get() = binding ?: bind<T>(getContentView()).also {
            it.lifecycleOwner = this@dataBinding
            binding = it
        }

    private fun FragmentActivity.getContentView(): View {
        return checkNotNull(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) {
            "Call setContentView or Use Activity's secondary constructor passing layout res id."
        }
    }

    private fun <T : ViewDataBinding> bind(view: View): T = DataBindingUtil.bind(view)!!
}

fun <T : ViewDataBinding> FragmentActivity.withBinding(withBinding: (binding: T) -> Unit) {
    val binding = bind<T>(getContentView()).also {
        it.lifecycleOwner = this@withBinding
    }
    withBinding(binding)
}

private fun <T : ViewDataBinding> bind(view: View): T = DataBindingUtil.bind(view)!!
private fun FragmentActivity.getContentView(): View {
    return checkNotNull(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) {
        "Call setContentView or Use Activity's secondary constructor passing layout res id."
    }
}
