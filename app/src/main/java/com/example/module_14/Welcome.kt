package com.example.module_14

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.OptionalPendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import kotlinx.android.synthetic.main.activity_welcome.*
import java.lang.NullPointerException

class Welcome : AppCompatActivity() ,GoogleApiClient.OnConnectionFailedListener{

    private var TAG= "Welcome"

    lateinit var googleApiClient : GoogleApiClient
    lateinit var gso : GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("108899055470-q63ok5qui46g4d2vs7pqfpih19t80510.apps.googleusercontent.com")
            .build()

        googleApiClient=GoogleApiClient.Builder(this)
            .enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
            .build()

      logoutBtn.setOnClickListener(object:View.OnClickListener {
        override fun onClick(view:View) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { status ->
         if (status.isSuccess) {
             var a=Intent(this@Welcome,MainActivity::class.java)
             startActivity(a)
            } else {
        Toast.makeText(applicationContext, "Session not close", Toast.LENGTH_LONG).show()
            }
        } }
        })

    }

    override fun onStart() {
        super.onStart()
       var opr=Auth.GoogleSignInApi.silentSignIn(googleApiClient);
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
       Log.d(TAG,"Somthing Want Wrong")
    }

    fun handleSignINResult(result : GoogleSignInResult){
        if(result.isSuccess)
        {
            var account=result.signInAccount
            name.text=account!!.displayName
            email.text=account.email
            userId.text=account.id
            account.serverAuthCode

            try{
                Glide.with(this).load(account.photoUrl).into(profileImage)
            }catch (e : NullPointerException)
            {
                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }
        }
        else{
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
