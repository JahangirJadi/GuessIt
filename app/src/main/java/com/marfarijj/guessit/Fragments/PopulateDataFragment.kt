package com.marfarijj.guessit.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.marfarijj.guessit.R
import com.marfarijj.guessit.databinding.FragmentPopulateDataBinding

class PopulateDataFragment : Fragment() {

    private lateinit var binding: FragmentPopulateDataBinding

    private lateinit var mDatabaseRef:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopulateDataBinding.inflate(inflater, container, false)

        context?.let { FirebaseApp.initializeApp(it) }

        mDatabaseRef =
            FirebaseDatabase.getInstance().getReference("Quiz")

        binding.btnAddData.setOnClickListener {
            addData(binding.etQuizCat.text.toString(),
            binding.etQuizData.text.toString())
        }


        return binding.root
    }

    private fun addData(cat: String, Data: String) {

        mDatabaseRef.child(cat).push().setValue(Data).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context, "Data added", Toast.LENGTH_LONG).show()
            }
        }


    }


}