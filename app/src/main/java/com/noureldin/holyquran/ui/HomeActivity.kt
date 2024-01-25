package com.noureldin.holyquran.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.noureldin.holyquran.R
import com.noureldin.holyquran.databinding.ActivityHomeBinding
import com.noureldin.holyquran.fragment.AhadeethFragment
import com.noureldin.holyquran.fragment.QuranFragment
import com.noureldin.holyquran.fragment.RadioFragment
import com.noureldin.holyquran.fragment.TasbeehFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startFragment(QuranFragment())
        initListeners()

    }
    fun initListeners(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.quranFragment){
                startFragment(QuranFragment())
            }else if (it.itemId == R.id.hadithFragment){
                startFragment(AhadeethFragment())
            }else if (it.itemId == R.id.tasbeehFragment){
                startFragment(TasbeehFragment())
            }else if (it.itemId == R.id.radioFragment){
                startFragment(RadioFragment())
            }


            return@setOnItemSelectedListener true
        }
    }
    private fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()

    }


}