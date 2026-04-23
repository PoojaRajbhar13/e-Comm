package com.example.myecomartapp.domain.repository

import android.app.Activity
import com.example.myecomartapp.core.util.Result
import kotlinx.coroutines.flow.Flow

interface  PaymentRepository {

    fun startPayment(amount : Long, activity: Activity) : Flow<Result<String>>
}