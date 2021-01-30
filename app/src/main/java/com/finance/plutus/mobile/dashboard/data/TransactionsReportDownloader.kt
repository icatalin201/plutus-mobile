package com.finance.plutus.mobile.dashboard.data

import android.content.Context
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.NotificationManager
import com.finance.plutus.mobile.transactions.data.TransactionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
Plutus Finance
Created by Catalin on 1/30/2021
 **/
class TransactionsReportDownloader(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams), KoinComponent {

    private val transactionRepository: TransactionRepository by inject()

    override fun doWork(): Result {
        val year = inputData.getString("year") ?: "2021"
        val notificationId = 10
        NotificationManager.createNotification(
            applicationContext,
            R.string.downloading,
            notificationId
        )
        val inputStream = transactionRepository
            .downloadDocument(year)
            .blockingGet()
        val file =
            File(
                applicationContext
                    .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                "transactions.pdf"
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