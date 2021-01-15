package com.marfarijj.guessit.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.navigation.fragment.navArgs
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.marfarijj.guessit.R
import com.marfarijj.guessit.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    val args: ResultFragmentArgs by navArgs()
private lateinit var binding : FragmentResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater,container, false)

        binding.tvWinner.setText(args.winner)

        return binding.root
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



}