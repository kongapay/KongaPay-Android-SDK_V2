package com.iconic_dev.kongapay_android_sdk_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kongapay.paymentgatewayv2.Params
import com.kongapay.paymentgatewayv2.PaymentGatewaySdk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        pay_kpg.setOnClickListener(View.OnClickListener {

            val merchantId = BuildConfig.MERCHANT_KEY
            val merchantKey = BuildConfig.MERCHANT_KEY
            val description = sdk_description.text.toString()
            val amountInKobo = Integer.parseInt(sdk_amount.text.toString())
            val customerEmail = sdk_email.text.toString()
            val customerPhone = sdk_phone.text.toString()

            val params = Params.create(
                merchantId,
                merchantKey,
                generateTransactionRef(),
                description,
                amountInKobo,
                customerEmail,
                customerPhone
            )


            // YOU SHOULD ADD YOUR OWN CUSTOMER_ID
            params.customerId = "1"

            PaymentGatewaySdk.initiatePayment(REQUEST_CODE, this@MainActivity, params)
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {

            val result = PaymentGatewaySdk.getResult(data)


            val isSuccessful = result.isSuccessful
            val status = result.status
            val ref = result.reference

            Toast.makeText(this, status + if (isSuccessful) ": $ref" else "", Toast.LENGTH_LONG)
                .show()

            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun generateTransactionRef(): String {

        return "REF" + System.currentTimeMillis()
    }
}

