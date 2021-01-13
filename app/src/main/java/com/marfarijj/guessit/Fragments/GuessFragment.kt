package com.marfarijj.guessit.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.model.content.CircleShape
import com.marfarijj.guessit.R
import com.marfarijj.guessit.databinding.FragmentGuessBinding
import douglasspgyn.com.github.circularcountdown.CircularCascadeCountdown
import douglasspgyn.com.github.circularcountdown.CircularCountdown
import douglasspgyn.com.github.circularcountdown.listener.CascadeListener
import douglasspgyn.com.github.circularcountdown.listener.CircularListener


class GuessFragment : Fragment() {

    private lateinit var player: MediaPlayer

    private lateinit var binding: FragmentGuessBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuessBinding.inflate(inflater, container, false)

        player = MediaPlayer.create(context, R.raw.alarm)
        binding.circularCountdown.create(1, 10, CircularCountdown.TYPE_SECOND)
            .listener(object : CircularListener {
                override fun onTick(progress: Int) {
                    if (progress == 10) {
                        Log.d("ActivityTimer", "onTick: $progress")
                        binding.circularCountdown.stop()

                        playSound()


                    }
                }

                override fun onFinish(newCycle: Boolean, cycleCount: Int) {

                }
            })
            .start()

        return binding.root

    }

    private fun playSound() {

        if (view?.isShown!!){
            player.start()
            val r2 = Runnable {
                player.stop()

            }
            Handler().postDelayed(r2, 3000)
        }


    }

    override fun onPause() {
        super.onPause()

        if (player.isPlaying) {
            player.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (player.isPlaying) {
            player.stop()
        }
    }


}