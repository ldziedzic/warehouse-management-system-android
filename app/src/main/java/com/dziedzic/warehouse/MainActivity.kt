package com.dziedzic.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dziedzic.warehouse.Entity.AuthTokenDTO
import com.dziedzic.warehouse.Entity.RequestManagerEntity
import com.dziedzic.warehouse.Entity.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import com.dziedzic.warehouse.Rest.APIClient
import com.google.android.gms.common.api.GoogleApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1
    private val TAG = "SignInActivity"
    private var mGoogleApiClient: GoogleApiClient? = null
    protected val authService = APIClient.getAuthService()
    protected val userService = APIClient.getUserService()

    companion object {
        val user = User()
        val requestsManager = BackgroundRequestsManager()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun signInGoogle(view: View) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("523539799843-hccnda578nhkun2kvmo279hd64u2q4ij.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
        mGoogleSignInClient.signOut()
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
            val idToken = account.idToken
            authorizeOAuth(idToken)

            //updateUI(account)
        } catch (e: ApiException) {
            Log.w("handleSignInResult", "handleSignInResult:error", e)
            //updateUI(null)
        }
    }


    fun authorizeOAuth(idToken: String?) {
        val call = authService.signInOAuth(idToken)
        call.enqueue(getSignInOAuthCallback())
    }


    private fun getSignInOAuthCallback(): Callback<AuthTokenDTO> {
        return object : Callback<AuthTokenDTO> {
            override fun onResponse(call: Call<AuthTokenDTO>, response: Response<AuthTokenDTO>) {
                Log.i(TAG, response.message())
                if(response.isSuccessful()) {
                    val refreshToken = response.body()?.idToken;
                    user.refreshToken = refreshToken
                    getCurrentUser(user.bearerToken)
                } else {
                    user.refreshToken = ""
                }
            }

            override fun onFailure(call: Call<AuthTokenDTO>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }


    fun getCurrentUser(bearerToken: String) {
        val call = userService.getCurrentUser(bearerToken)
        call.enqueue(getCurrentUserCallback())
    }


    private fun getCurrentUserCallback(): Callback<User> {
        return object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.i(TAG, response.message())
                if(response.isSuccessful()) {
                    val userDTO = response.body()
                    if (userDTO != null) {
                        user.name = userDTO.name
                        user.email = userDTO.email
                        user.role = userDTO.role
                    }

                    startNextActivity()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        }
    }

    private fun startNextActivity() {
        val nextScreen = Intent(
            applicationContext,
            ProductManagerBrowser::class.java
        )
        startActivity(nextScreen)
        finish()
    }
}