package com.marfarijj.guessit.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.marfarijj.guessit.R
import com.marfarijj.guessit.databinding.FragmentStartScreenBinding

private val admobUnitIt = "ca-app-pub-8944787818061216/3622125596"

class StartScreenFragment : Fragment() {

    private lateinit var binding : FragmentStartScreenBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
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

        binding.ivBtnShare.setOnClickListener{
            shareApp()
        }

        binding.btnHtp.setOnClickListener {
            navController.navigate(R.id.action_nav_to_htp)
        }

        binding.btnStart.setOnClickListener {
            navController.navigate(R.id.action_nav_to_guess)
        }

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

    private fun exitDialog() {
        lateinit var alertDialog: LottieAlertDialog
        alertDialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_QUESTION)
            .setTitle("Confirmation")
            .setDescription("Are you sure you want to quit?")
            .setPositiveText("Yes")
            .setNegativeText("No")
            .setPositiveButtonColor(Color.parseColor("#069509"))
            .setNegativeButtonColor(Color.parseColor("#FF03DAC5"))
            .setPositiveTextColor(Color.parseColor("#ffffff"))
            .setNegativeTextColor(Color.parseColor("#ffffff"))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {

                    activity?.finish()
                    dialog.dismiss()
                }
            }

            ).setNegativeListener(object: ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {

                    dialog.dismiss()

                }
            })

            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()


    }

    override fun onAttach(@NonNull context: Context) {
        super.onAttach(context)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }


private fun shareApp(){
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_TEXT, "Checkout new app: https://play.google.com/store/apps/details?id=com.marfarijj.guessit")
    shareIntent.type = "text/plain"
    startActivity(Intent.createChooser(shareIntent,"send to"))
}


}