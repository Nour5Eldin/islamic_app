package com.noureldin.holyquran.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.noureldin.holyquran.R
import com.noureldin.holyquran.databinding.ActivityHomeBinding
import com.noureldin.holyquran.fragment.HadithFragment
import com.noureldin.holyquran.fragment.QuranFragment
import com.noureldin.holyquran.fragment.RadioFragment
import com.noureldin.holyquran.fragment.TasbeehFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.quranFragment ->{
                    replaceFragment(QuranFragment())
                    true
                }
                R.id.hadithFragment ->{
                    replaceFragment(HadithFragment())
                    true
                }
                R.id.tasbeehFragment ->{
                    replaceFragment(TasbeehFragment())
                    true
                }
                R.id.radioFragment ->{
                    replaceFragment(RadioFragment())
                    true
                }
                else -> false

            }
        }
        replaceFragment(QuranFragment())
    }

    private fun  replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }


}