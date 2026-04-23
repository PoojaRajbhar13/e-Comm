package com.example.myecomartapp.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class  PaymentViewModel @Inject constructor(private  val paymentRepository: PaymentRepository):  ViewModel() {

    private val _paymentState = MutableStateFlow<Result<String>>(Result.Loading)
    val paymentState =  _paymentState.asStateFlow()

    fun payNow(amount : Long, activity: Activity){
        viewModelScope.launch {
            paymentRepository.startPayment(amount, activity).collectLatest { result ->
                _paymentState.value = result
            }
        }
    }


    fun setPaymentResult(result: Result<String>){
        _paymentState.value = result
    }

    }



