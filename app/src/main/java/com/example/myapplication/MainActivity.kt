package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem

class MainActivity : ComponentActivity() {
    companion object {
        var timeSec:Int=0;
    }
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonotheractivity: Button = findViewById(R.id.idBtnRewardVideo)
        val textActivity: TextView = findViewById(R.id.test1)
        buttonotheractivity.setOnClickListener {

            var adRequest = AdRequest.Builder().build()

            InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    textActivity.text="Not Loaded"
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    textActivity.text="Ad Loaded"
                }
            })
            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    textActivity.text="Ad was clicked."
                    Log.d(TAG, "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    Log.d(TAG, "Ad dismissed fullscreen content.")
                    textActivity.text="Ad dismissed fullscreen content."
                    mInterstitialAd = null
                    val intentLogin = Intent(this@MainActivity, SecondActivity::class.java)
                    startActivity(intentLogin)
                }

                /*override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    // Called when ad fails to show.
                    textActivity.text="Ad failed to show fullscreen content."
                    mInterstitialAd = null
                }*/

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "Ad recorded an impression.")
                    textActivity.text="Ad recorded an impression."
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    textActivity.text="Ad showed fullscreen content."
                    Log.d(TAG, "Ad showed fullscreen content.")
                }
            }
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)

            } else {
                textActivity.text="The interstitial ad wasn't ready yet."
            }



            //val intentLogin = Intent(this, SecondActivity::class.java)
            //startActivity(intentLogin)
        }


    }

}
