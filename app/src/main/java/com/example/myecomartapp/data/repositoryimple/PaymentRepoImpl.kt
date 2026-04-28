package com.example.myecomartapp.data.repositoryimple

import android.app.Activity
import com.example.myecomartapp.R
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.repository.PaymentRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import javax.inject.Inject

class PaymentRepoImpl @Inject constructor(private val firestore: FirebaseFirestore) : PaymentRepository {

    override fun startPayment(amount: Long, activity: Activity): Flow<Result<String>> =
        callbackFlow {
            try {
                trySend(Result.Loading)
                val key = try {
                    firestore.collection("payment").document("razorpay").get().await()
                        .getString("RAZORPAY_API")
                } catch (e: Exception) {
                    null

                }
                val checkout = Checkout().apply {
                    setKeyID(key)
                    setImage(R.drawable.applogo)
                }
                val paymentOptions = JSONObject().apply {
                    put("Name", "Stylish")
                    put("Description", "OrderPayment")
                    put("Amount", amount)
                    put("currency", "INR")
                }

                checkout.open(activity, paymentOptions)

            } catch (e: Exception) {
                trySend(Result.Failure(e.message ?: "Something went wrong"))

            }
            awaitClose { }
        }


}