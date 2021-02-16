package com.finance.plutus.mobile.ext

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import com.finance.plutus.mobile.R
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
fun LifecycleOwner.showConfirmationDialog(
        context: Context,
        @StringRes message: Int,
        callback: Runnable
) {
    val dialog = AlertDialog.Builder(context, R.style.Theme_Plutus_Dialog)
            .setTitle(R.string.app_name)
            .setMessage(message)
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.confirm) { dialog, _ ->
                dialog.dismiss()
                callback.run()
            }
    dialog.show()
}

fun LifecycleOwner.showInformationDialog(
        context: Context,
        message: String
) {
    val dialog = AlertDialog.Builder(context, R.style.Theme_Plutus_Dialog)
            .setTitle(R.string.app_name)
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
    dialog.show()
}

fun LifecycleOwner.showListDialog(
        context: Context,
        @StringRes title: Int,
        items: Array<String>,
        callback: (position: Int) -> Unit
) {
    val dialog = AlertDialog.Builder(context, R.style.Theme_Plutus_Dialog)
            .setTitle(title)
            .setItems(items) { dialog, which ->
                dialog.dismiss()
                callback(which)
            }
    dialog.show()
}

fun LifecycleOwner.showListDialog(
        context: Context,
        items: Array<String>,
        callback: (position: Int) -> Unit
) {
    showListDialog(context, R.string.app_name, items, callback)
}

fun LifecycleOwner.showInputDialog(
        context: Context,
        initialValue: String,
        callback: (value: String) -> Unit
) {
    val editText = EditText(context)
    editText.imeOptions = EditorInfo.IME_ACTION_DONE
    editText.isSingleLine = true
    editText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
    editText.setText(initialValue)
    val dialog = AlertDialog.Builder(context, R.style.Theme_Plutus_Dialog)
            .setTitle(R.string.app_name)
            .setMessage(R.string.update_serial_title)
            .setView(editText)
            .setNegativeButton(
                    R.string.cancel
            ) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            .setPositiveButton(
                    R.string.confirm
            ) { dialog: DialogInterface, _: Int ->
                val value = editText.text.toString()
                callback(value)
                dialog.dismiss()
            }.create()
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