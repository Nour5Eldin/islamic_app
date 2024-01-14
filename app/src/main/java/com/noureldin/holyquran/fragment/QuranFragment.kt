package com.noureldin.holyquran.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.noureldin.holyquran.databinding.FragmentQuranBinding



class QuranFragment : Fragment() {
    private lateinit var binding: FragmentQuranBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuranBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }








}