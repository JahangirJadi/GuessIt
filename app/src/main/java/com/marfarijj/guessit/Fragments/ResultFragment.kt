package com.marfarijj.guessit.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
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


}