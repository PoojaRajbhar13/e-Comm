package com.example.myecomartapp.presentation.screen.cartscreen

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myecomartapp.PaymentActivity
import com.example.myecomartapp.R
import com.example.myecomartapp.presentation.common.FailureComponent
import com.example.myecomartapp.presentation.common.LoadingIndicator
import com.example.myecomartapp.presentation.componentes.cartcomponent.CartCard
import com.example.myecomartapp.presentation.componentes.cartcomponent.CartTopAppBar
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.viewmodel.CartViewModel

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val cartState by cartViewModel.state.collectAsState()

    // Professional Result Handling
    val paymentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val status = data?.getStringExtra("Status")
        
        if (status.equals("Success", ignoreCase = true)) {
            cartViewModel.clearCart()
            Toast.makeText(context, "Order Placed Successfully! \uD83C\uDF89", Toast.LENGTH_LONG).show()
            // Navigate to Home screen after success and clear stack
            navController.navigate(Route.HomeScreen) {
                popUpTo(Route.HomeScreen) { inclusive = true }
            }
        } else {
            val message = if (status == "Failed") "Payment Failed" else "Payment Cancelled"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.WhiteSmoke),
        topBar = {
            CartTopAppBar {
                onNavigateBack()
            }
        }
    ) { innerPadding ->
        BackHandler {
            onNavigateBack()
        }

        if (cartState.cartItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.addcart),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )

                Button(
                    onClick = { navController.navigate(Route.HomeScreen) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.Crimson)
                    ),
                ) {
                    Text(
                        text = "Start Shopping",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        } else {
            when {
                cartState.isLoading -> {
                    LoadingIndicator()
                }

                cartState.error != null -> {
                    FailureComponent { cartViewModel.loadCartItems() }
                }

                else -> {
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(cartState.cartItems) { cartItem ->
                            CartCard(
                                onIncrease = {
                                    cartViewModel.updateQuantity(
                                        cartItem.product.id,
                                        cartItem.quantity + 1
                                    )
                                },
                                onDecrease = {
                                    if (cartItem.quantity > 1)
                                        cartViewModel.updateQuantity(
                                            cartItem.product.id,
                                            cartItem.quantity - 1
                                        ) else {
                                        cartViewModel.removeFromCart(cartItem.product.id)
                                    }
                                },
                                onDelete = {
                                    Toast.makeText(
                                        context,
                                        "Removed from cart",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    cartViewModel.removeFromCart(cartItem.product.id)
                                },
                                cartItem = cartItem,
                                quantity = cartItem.quantity.toString()
                            )
                        }
                        item {
                            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                                Box(
                                    modifier = Modifier
                                        .background(color = colorResource(R.color.Silver))
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "Fast Delivery",
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = colorResource(R.color.WhiteSmoke),
                                            fontStyle = FontStyle.Italic
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.Filled.AirportShuttle,
                                            contentDescription = null,
                                            tint = colorResource(R.color.WhiteSmoke),
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "Total Amount",
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = "₹${cartState.totalPrice}",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 22.sp,
                                            color = colorResource(R.color.Green)
                                        )
                                    }

                                    Button(
                                        onClick = {
                                            val totalAmount = cartState.totalPrice.toDoubleOrNull() ?: 0.0
                                            if (totalAmount > 0) {
                                                // Convert to Paise for Razorpay (Professional approach)
                                                // Razorpay expects amount in the smallest currency unit (paise for INR)
                                                val amountInPaise = Math.round(totalAmount * 100)
                                                val intent = Intent(context, PaymentActivity::class.java).apply {
                                                    putExtra("amount", amountInPaise)
                                                }
                                                paymentLauncher.launch(intent)
                                            } else {
                                                Toast.makeText(context, "Invalid amount", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(R.color.Crimson)
                                        ),
                                        modifier = Modifier.height(50.dp)
                                    ) {
                                        Text(
                                            text = "Place Order",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                HorizontalDivider(
                                    modifier = Modifier.padding(top = 16.dp),
                                    thickness = 1.dp,
                                    color = Color.LightGray.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}