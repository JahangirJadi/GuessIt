package com.marfarijj.guessit.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.marfarijj.guessit.R
import com.marfarijj.guessit.databinding.FragmentGuideBinding
private val admobUnitIt = "ca-app-pub-8944787818061216/9337857832"

class GuideFragment : Fragment() {

    private lateinit var binding:FragmentGuideBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGuideBinding.inflate(inflater,container, false)
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