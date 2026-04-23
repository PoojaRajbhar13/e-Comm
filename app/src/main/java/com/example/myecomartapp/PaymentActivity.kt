package com.example.myecomartapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.presentation.viewmodel.PaymentViewModel
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity: ComponentActivity(), PaymentResultListener {

    private val viewModel : PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val amount = intent.getLongExtra("amount", 0L)
        viewModel.payNow(amount, this)
    }

    override fun onPaymentSuccess(paymentId : String){
        viewModel.setPaymentResult(Result.Success(paymentId))
        val resultIntent =  Intent().apply {
            putExtra("Status", "Success")
            putExtra("paymentId", paymentId)
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onPaymentError(errorCode: Int, response: String?){
        viewModel.setPaymentResult(Result.Failure(response?: " Payment Failed"))

        val resultIntent =  Intent().apply {
            putExtra("Status", "Failed")
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}