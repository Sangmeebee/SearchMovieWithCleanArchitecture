package com.sangmeebee.searchmovie.util.social_login.google

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.sangmeebee.searchmovie.BuildConfig

class GoogleProxyActivity : AppCompatActivity() {
//
//    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
//
//    private lateinit var oneTapClient: SignInClient
//    private lateinit var signInRequest: BeginSignInRequest
//    private lateinit var signUpRequest: BeginSignInRequest
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        setFinishOnTouchOutside(false)
//
//        resultLauncher =
//            registerForActivityResult(StartActivityForResult()) { result ->
//                activityRequest?.listener?.onActivityResult(result.resultCode, result.data)
//                finish()
//            }
//
//        setGoogleLogin()
//
//        resultLauncher.launch(activityRequest.intent)
//    }
//
//    override fun onDestroy() {
//        activityRequest = null
//        super.onDestroy()
//    }
//
//    private fun setGoogleLogin() {
//
//        oneTapClient = Identity.getSignInClient(this)
//
//        signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            .build()
//
//        signUpRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
//                    // Show all accounts on the device.
//                    .setFilterByAuthorizedAccounts(false)
//                    .build())
//            .build()
//    }
//
//    private fun showSignIn() {
//        oneTapClient.beginSignIn(signInRequest)
//            .addOnSuccessListener(this) { result ->
//                try {
//                    val intentSenderRequest =
//                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
//                    oneTabResult.launch(intentSenderRequest)
//
//                } catch (e: IntentSender.SendIntentException) {
//                    Log.e("Sangmeebee", "Couldn't start One Tap UI: ${e.localizedMessage}")
//                }
//            }
//            .addOnFailureListener(this) { e ->
//                // No Google Accounts found. Just continue presenting the signed-out UI.
//                Log.d("Sangmeebee", e.localizedMessage.toString())
//                showSignUp()
//            }
//    }

//    private fun showSignUp() {
//        oneTapClient.beginSignIn(signUpRequest)
//            .addOnSuccessListener(this) { result ->
//                try {
//                    val intentSenderRequest =
//                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
//                    oneTabResult.launch(intentSenderRequest)
//
//                } catch (e: IntentSender.SendIntentException) {
//                    Log.e("Sangmeebee", "Couldn't start One Tap UI: ${e.localizedMessage}")
//                }
//            }
//            .addOnFailureListener(this) { e ->
//                // No Google Accounts found. Just continue presenting the signed-out UI.
//                Log.d("Sangmeebee", e.localizedMessage.toString())
//            }
//    }
//
//    companion object {
//        private var activityRequest: ActivityResultCallback? = null
//
//        fun startActivityForLogin(
//            context: Context,
//            listener: ActivityResultCallback,
//        ) {
//            activityRequest = ActivityRequest(intent, listener)
//
//            context.startActivity(Intent(context, GoogleProxyActivity::class.java))
//        }
//    }
}