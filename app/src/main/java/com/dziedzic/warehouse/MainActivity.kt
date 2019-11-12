package com.dziedzic.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dziedzic.warehouse.Entity.AuthTokenDTO
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import java.net.HttpURLConnection

import com.dziedzic.warehouse.Rest.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class MainActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 9001
    private val TAG = "SignInActivity"
    protected val authService = APIClient.getAuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()


    }

    fun signIn(view: View) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("523539799843-hccnda578nhkun2kvmo279hd64u2q4ij.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.getIdToken()
            authorizeOAuth(idToken)

            //updateUI(account)
        } catch (e: ApiException) {
            Log.w("handleSignInResult", "handleSignInResult:error", e)
            //updateUI(null)
        }
    }


    fun authorizeOAuth(idToken: String?) {
        val call = authService.signInOAuth(idToken)
        call.enqueue(getFetchCallback())
    }


    private fun getFetchCallback(): Callback<AuthTokenDTO> {
        return object : Callback<AuthTokenDTO> {
            override fun onResponse(call: Call<AuthTokenDTO>, response: Response<AuthTokenDTO>) {
                Log.i(TAG, response.message())
                if (response.code() == HttpURLConnection.HTTP_OK) {

                } else if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {

                } else if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {

                } else if (response.code() == HttpURLConnection.HTTP_UNAVAILABLE) {

                } else {

                }
            }

            override fun onFailure(call: Call<AuthTokenDTO>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }
}