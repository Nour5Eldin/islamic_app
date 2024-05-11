package com.noureldin.holyquran.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.noureldin.holyquran.R
import com.noureldin.holyquran.databinding.FragmentTasbeehBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasbeehFragment : Fragment() {
    private var _binding: FragmentTasbeehBinding? =null
    private val binding get() = _binding!!
    private lateinit var clickCounter : TextView
    private lateinit var azkarButton : Button
    private var roundCounter = 0
    private val clickThreshold = 33
    private val loopSize = 5
    private var buttonTexts = listOf("سبحان الله","الحمد لله","الله أكبر","لا حول ولا قوة إلا بالله","أستغفر الله")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasbeehBinding.inflate(inflater, container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickCounter = binding.counterNumberTasbihTv
        azkarButton = binding.AzkarButton
        updateButtonText()

        var timeClicked =0
        binding.AzkarButton.setOnClickListener {
            rotateImage()
           timeClicked +=1
            clickCounter.text = timeClicked.toString()
            if (timeClicked % clickThreshold==0){
                changeButtonText()
              timeClicked ++


            }
        }
    }

    private fun changeButtonText() {
            val newText = buttonTexts[roundCounter % loopSize]
            azkarButton.text = newText
            roundCounter++

    }
    private fun updateButtonText() {
        azkarButton.text = "لا اله إلا الله "
    }

    private fun rotateImage() {
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.anime_rotate)
        binding.bodySeb7a.startAnimation(rotateAnimation)
    }





}