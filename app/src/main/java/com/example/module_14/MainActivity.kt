package com.example.module_14

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import kotlinx.android.synthetic.main.activity_main.*
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.AccessToken
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class MainActivity : AppCompatActivity() , GoogleApiClient.OnConnectionFailedListener{

    private var TAG=  "Login"
    private val EMAIL = "email"
    private final val RC_SIGN_IN =1
    lateinit var callbackManager : CallbackManager
    lateinit var googleApiClint : GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(applicationContext)
        callbackManager = CallbackManager.Factory.create()
        login_button.setPublishPermissions(Arrays.asList("publish_actions"));

//        login_button.setReadPermissions(Arrays.asList(EMAIL,"user_status","public_profile", "user_friends"))
        updateHashKey()

        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = loginResult.accessToken
                var intent=Intent(this@MainActivity,LoginScreenFacebook::class.java)
                startActivity(intent)
            }

            override fun onCancel() {
                Log.d("FaceBook","Somthing Want Wrong")
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
        var gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("108899055470-q63ok5qui46g4d2vs7pqfpih19t80510.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleApiClint=GoogleApiClient.Builder(this)
            .enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
            .build()

        sign_in_button.setOnClickListener {
            var intent= Auth.GoogleSignInApi.getSignInIntent(googleApiClint)
            startActivityForResult(intent,RC_SIGN_IN)
        }

    }

    override fun onStart() {
        super.onStart()
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if(isLoggedIn)
        {
            var intent=Intent(this,LoginScreenFacebook::class.java)
            startActivity(intent)
        }
        var opr=Auth.GoogleSignInApi.silentSignIn(googleApiClint);
        if(opr.isDone){
            var result=opr.get()
            handleSignINResult(result)

        }
        else{
            opr.setResultCallback { ResultCallback<GoogleSignInResult>()
            {
                handleSignINResult(it)
            } }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
       Log.d(TAG,"Somithing Want Wrong")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN)
        {
            var result=Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSinginResult(result)
        }
    }

    private fun updateHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                "com.example.module_14",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", android.util.Base64.encodeToString(md.digest(),  android.util.Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }
    }



    fun handleSinginResult(result : GoogleSignInResult)
    {
        if(result.isSuccess){
            var intent=Intent(this,Welcome::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"Sign in cancel",Toast.LENGTH_SHORT).show()
        }
    }
    fun handleSignINResult(result : GoogleSignInResult){
        if(result.isSuccess)
        {
            var intent=Intent(this,Welcome::class.java)
            startActivity(intent)

        }
        else{

        }
    }

}
