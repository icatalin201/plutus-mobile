package com.finance.plutus.mobile.dashboard.data

import android.content.Context
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.NotificationManager
import com.finance.plutus.mobile.invoices.data.InvoiceRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
Plutus Finance
Created by Catalin on 1/30/2021
 **/
class InvoicesArchiveDownloader(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams), KoinComponent {

    private val invoiceRepository: InvoiceRepository by inject()

    override fun doWork(): Result {
        val notificationId = 11
        NotificationManager.createNotification(
            applicationContext,
            R.string.downloading,
            notificationId
        )
        val inputStream = invoiceRepository
            .downloadArchive()
            .blockingGet()
        val file =
            File(
                applicationContext
                    .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                "invoices.zip"
            )
        writeStream(inputStream, file)
        NotificationManager.createNotification(
            applicationContext,
            R.string.downloaded,
            notificationId
        )
        return Result.success()
    }

    private fun writeStream(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024)
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }
}