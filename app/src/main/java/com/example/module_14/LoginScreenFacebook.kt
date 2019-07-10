package com.example.module_14

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_login_screen_facebook.*
import org.json.JSONException
import org.json.JSONObject
import com.facebook.share.widget.ShareDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.view.MenuItemCompat.setContentDescription
import com.facebook.share.model.*

import java.io.IOException


class LoginScreenFacebook : AppCompatActivity() {


    lateinit var accsstoken: AccessToken
    private val PICK_IMAGE_REQUEST = 1
    private val PICK_VIDEO_REQUEST = 2
    var shareDialog: ShareDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen_facebook)
        logoutBtn.setOnClickListener {
            LoginManager.getInstance().logOut();
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }


        shareDialog = ShareDialog(this);
        accsstoken = AccessToken.getCurrentAccessToken()
        getUserProfile(accsstoken)

        btnShareLinks.setOnClickListener {
            if(ShareDialog.canShow(ShareLinkContent::class.java)){
                var link=ShareLinkContent.Builder()
                    .setQuote("This may be used to replace setTitle and setDescription.")
                    .setContentUrl(Uri.parse("https://hrms.solutionanalysts.com/public/#/lms/schedules"))
                    .build()

                shareDialog!!.show(link)

            }

        }

        btnSharePhotos.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }
        btnShareVideos.setOnClickListener {
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST)
        }

    }

    //for getting data from facebook and setting in ui
    private fun getUserProfile(currentAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(currentAccessToken)
        { `object`, response ->
            try {
                val first_name = `object`.getString("first_name")
                val last_name = `object`.getString("last_name")
                val mail = `object`.getString("email")
                val id = `object`.getString("id")
                val image_url = "https://graph.facebook.com/$id/picture?type=normal"

                name.text = "First Name: $first_name\nLast Name: $last_name"
                email.text = mail
                userId.text = id
                Glide.with(this).load(image_url).into(profileImage)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

     val parameters = Bundle()
     parameters.putString("fields", "first_name,last_name,email,id")
      request.parameters = parameters
        request.executeAsync()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

                var image: Bitmap
                try {
                    image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                } catch (e: IOException) {
                    e.printStackTrace();
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
                }
                var photo = SharePhoto.Builder()
                    .setBitmap(image)
                    .build();

                if (ShareDialog.canShow(SharePhotoContent::class.java)) {
                    var sharePhotoContent = SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                    shareDialog!!.show(sharePhotoContent);
                }


            }
            else if(requestCode==PICK_VIDEO_REQUEST && data!=null && data.getData()!=null){

                var shareVideo =  ShareVideo.Builder()
                    .setLocalUrl(data.getData())
                    .build();
                if (ShareDialog.canShow(ShareVideoContent::class.java)) {
                        var shareVideoContent =  ShareVideoContent.Builder()
                            .setVideo(shareVideo)
                            .build();
                        shareDialog!!.show(shareVideoContent);
                    }
            }
        }
    }


}
