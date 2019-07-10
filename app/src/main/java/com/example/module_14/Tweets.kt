package com.example.module_14

import android.app.ListActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.twitter.sdk.android.tweetui.UserTimeline
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TimelineResult
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import kotlinx.android.synthetic.main.activity_tweets.*
import com.twitter.sdk.android.tweetcomposer.TweetComposer




class Tweets : ListActivity() {
    private val userTimelineRecyclerView: RecyclerView? = null
    private val swipeRefreshLayout: SwipeRefreshLayout? = null
    private val adapter: TweetTimelineRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)

        btnLogin.setOnClickListener {
            val builder = TweetComposer.Builder(this)
                .text("just setting up my Twitter Kit.")
            builder.show()
        }


//         var userTimeline =  UserTimeline.Builder()
//             .maxItemsPerRequest(50)
//             .includeReplies(true)
//             .includeRetweets(true)
//            .screenName("narendramodi")
//            .build();

//        userTimeline?.next(20,object : Callback<TimelineResult<Tweet>>(){
//            override fun success(result: Result<TimelineResult<Tweet>>?) {
//                var i = result?.data?.items?.size
//            }
//
//            override fun failure(exception: TwitterException?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })
//        var adapter = TweetTimelineListAdapter.Builder(this)
//            .setTimeline(userTimeline)
//            .build();
//          setListAdapter(adapter);
    }
}

