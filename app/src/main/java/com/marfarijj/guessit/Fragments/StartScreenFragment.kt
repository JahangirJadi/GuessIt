package com.marfarijj.guessit.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.marfarijj.guessit.R
import com.marfarijj.guessit.databinding.FragmentStartScreenBinding

val admobUnitIt = "ca-app-pub-3940256099942544/6300978111"

class StartScreenFragment : Fragment() {

    private lateinit var binding : FragmentStartScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartScreenBinding.inflate(inflater,container,false)
        MobileAds.initialize(
            context
        )
        initBannerAd()

        return binding.root
    }

    private fun initBannerAd() {
        try {
            val adView = AdView(context)
            adView.adSize = AdSize.BANNER

            adView.adUnitId = admobUnitIt

            val adRequest = AdRequest.Builder().build()
            binding.adView.loadAd(adRequest)
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }




}