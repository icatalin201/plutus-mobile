package com.finance.plutus.mobile.ext

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R


/**
Plutus Finance
Created by Catalin on 1/30/2021
 **/
object AdapterExtensions {

    const val ITEM_TOP = 1
    const val ITEM_MIDDLE = 2
    const val ITEM_BOTTOM = 3
    const val ITEM_SINGLE = 4

    fun RecyclerView.ViewHolder.setupLayout(itemType: Int, context: Context, layout: View) {
        val params = layout.layoutParams as RecyclerView.LayoutParams
        params.marginStart = convertValueToPx(16.0f, context)
        params.marginEnd = convertValueToPx(16.0f, context)
        when (itemType) {
            ITEM_TOP -> {
                params.topMargin = convertValueToPx(16.0f, context)
                params.bottomMargin = 0
                layout.setBackgroundResource(R.drawable.item_top_background)
            }
            ITEM_MIDDLE -> {
                params.bottomMargin = 0
                params.topMargin = 0
                layout.setBackgroundResource(R.drawable.item_middle_background)
            }
            ITEM_BOTTOM -> {
                params.topMargin = 0
                params.bottomMargin = convertValueToPx(16.0f, context)
                layout.setBackgroundResource(R.drawable.item_bottom_background)
            }
            ITEM_SINGLE -> {
                params.topMargin = convertValueToPx(16.0f, context)
                params.bottomMargin = convertValueToPx(16.0f, context)
                layout.setBackgroundResource(R.drawable.item_top_bottom_background)
            }
        }
        layout.layoutParams = params
    }

    private fun RecyclerView.ViewHolder.convertValueToPx(value: Float, context: Context): Int {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                context.resources.displayMetrics
        ).toInt()
    }

}