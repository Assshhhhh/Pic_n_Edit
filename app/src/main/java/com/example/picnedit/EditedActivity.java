package com.example.picnedit;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.example.picnedit.databinding.ActivityEditedBinding;
import com.example.picnedit.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class EditedActivity extends AppCompatActivity {

    ActivityEditedBinding binding;
    private InterstitialAd mInterstitialAd;

    ImageView ic_home,ic_back;
    ImageView ic_share,ic_fb,ic_insta,ic_whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.image.setImageURI(getIntent().getData());

        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditedActivity.this,MainActivity.class));
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(EditedActivity.this, DsPhotoEditorActivity.class));

                Intent dsPhotoEditorIntent = new Intent(EditedActivity.this, DsPhotoEditorActivity.class);
                dsPhotoEditorIntent.setData(getIntent().getData());
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Pico");
                int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
                startActivity(dsPhotoEditorIntent);
            }
        });
        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                String pic_txt = "Pic n' Edit_Image";
                shareIntent.putExtra(Intent.EXTRA_TEXT,pic_txt);
                shareIntent.putExtra(Intent.EXTRA_STREAM, getIntent().getData());
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(shareIntent,"Share using"));
            }
        });
        binding.facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setPackage("com.facebook.orca");
                shareIntent.setType("image/*");
                String pic_txt = "Pic n' Edit_Image";
                shareIntent.putExtra(Intent.EXTRA_TEXT,pic_txt);
                shareIntent.putExtra(Intent.EXTRA_STREAM, getIntent().getData());
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(EditedActivity.this, "Facebook not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.whatsappBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setPackage("com.whatsapp");
                shareIntent.setType("image/*");
                String pic_txt = "Pic n' Edit_Image";
                shareIntent.putExtra(Intent.EXTRA_TEXT,pic_txt);
                shareIntent.putExtra(Intent.EXTRA_STREAM, getIntent().getData());
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(EditedActivity.this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.instagrambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setPackage("com.instagram.android");
                shareIntent.setType("image/*");
                String pic_txt = "Pic n' Edit_Image";
                shareIntent.putExtra(Intent.EXTRA_TEXT,pic_txt);
                shareIntent.putExtra(Intent.EXTRA_STREAM, getIntent().getData());
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(EditedActivity.this, "Instagram not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });


/*
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                if(mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
            }
        });*/

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                        //
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });

                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(EditedActivity.this);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }


                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
        //

        //


    }
}
