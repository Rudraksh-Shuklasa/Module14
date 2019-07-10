package com.example.module_14

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import kotlinx.android.synthetic.main.activity_twitter_login.*
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.Twitter

import android.util.Log
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.TwitterConfig


class TwitterLogin : AppCompatActivity() {

    lateinit var loginButton : TwitterLoginButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(getString(R.string.twitter_api_key), getString(R.string.twitter_api_secret_kay)))
            .debug(true)
            .build()
        Twitter.initialize(config)

        setContentView(R.layout.activity_twitter_login)


        loginButton = findViewById(R.id.login_button)
        loginButton.setCallback(object : Callback<TwitterSession>() {
           override fun success(result: Result<TwitterSession>) {
                // Do something with result, which provides a TwitterSession for making API calls
                val session = TwitterCore.getInstance().sessionManager.activeSession
                val authToken = session.authToken
                //String token = authToken.token;
                //  String secret = authToken.secret;

                loginMethod(session)
            }


            override  fun failure(exception: TwitterException) {
                // Do something on failure
                Toast.makeText(applicationContext, "Login fail", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun loginMethod(session : TwitterSession)
    {
        var intent=Intent(this@TwitterLogin,Tweets::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton.onActivityResult(requestCode,resultCode,data)

    }
}
