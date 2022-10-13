package com.sangmeebee.searchmovie.util.social_login.google

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.sangmeebee.searchmovie.BuildConfig

class GoogleProxyActivity : AppCompatActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var signUpRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setFinishOnTouchOutside(false)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            activityResultCallback?.let { it(result, null) }
            finish()
        }
        setGoogleLogin()
        showSignIn()
    }

    override fun onDestroy() {
        activityResultCallback = null
        super.onDestroy()
    }

    private fun setGoogleLogin() {

        oneTapClient = Identity.getSignInClient(this)

        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()

        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()
    }

    private fun showSignIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    resultLauncher.launch(intentSenderRequest)

                } catch (e: IntentSender.SendIntentException) {
                    activityResultCallback!!(null, IntentSender.SendIntentException("Couldn't start One Tap UI: ${e.localizedMessage}"))
                    finish()
                }
            }
            .addOnFailureListener(this) { showSignUp() }
    }

    private fun showSignUp() {
        oneTapClient.beginSignIn(signUpRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    resultLauncher.launch(intentSenderRequest)

                } catch (e: IntentSender.SendIntentException) {
                    activityResultCallback!!(null, IntentSender.SendIntentException("Couldn't start One Tap UI: ${e.localizedMessage}"))
                    finish()
                }
            }
            .addOnFailureListener(this) { e ->
                activityResultCallback!!(null, IntentSender.SendIntentException("No Google Accounts found: ${e.localizedMessage}"))
                finish()
            }
    }

    companion object {
        private var activityResultCallback: ((ActivityResult?, Throwable?) -> Unit)? = null

        fun startActivityForLogin(
            context: Context,
            callback: (ActivityResult?, Throwable?) -> Unit,
        ) {
            activityResultCallback = callback

            context.startActivity(Intent(context, GoogleProxyActivity::class.java))
        }
    }
}