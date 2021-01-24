package com.finance.plutus.mobile.app.util

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import com.finance.plutus.mobile.R
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
fun LifecycleOwner.showDeleteConfirmationDialog(context: Context, callback: Runnable) {
    val dialog = AlertDialog.Builder(context)
        .setTitle(R.string.delete)
        .setMessage(R.string.delete_confirmation)
        .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        .setPositiveButton(R.string.confirm) { dialog, _ ->
            dialog.dismiss()
            callback.run()
        }
    dialog.show()
}

fun LifecycleOwner.showListDialog(
    context: Context,
    items: Array<String>,
    callback: (position: Int) -> Unit
) {
    val dialog = AlertDialog.Builder(context)
        .setTitle(R.string.app_name)
        .setItems(items) { dialog, which ->
            dialog.dismiss()
            callback(which)
        }
    dialog.show()
}

fun LifecycleOwner.showDateDialog(
    context: Context,
    date: String,
    callback: (date: String) -> Unit
) {
    val calendar: Calendar = date.toCalendar()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val day = calendar[Calendar.DAY_OF_MONTH]
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val start = Calendar.getInstance()
            start[Calendar.YEAR] = selectedYear
            start[Calendar.MONTH] = selectedMonth
            start[Calendar.DAY_OF_MONTH] = selectedDayOfMonth
            callback(start.time.toFormattedString())
        }, year, month, day
    )
    datePickerDialog.show()
}