package com.noureldin.holyquran.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noureldin.holyquran.data.Constant
import com.noureldin.holyquran.databinding.ActivityAhadeethDetailsBinding
import com.noureldin.holyquran.model.HadeethModel

class AhadeethDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAhadeethDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAhadeethDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var hadeeth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(Constant.HADEETH, HadeethModel::class.java)
        } else {
            intent.getSerializableExtra(Constant.HADEETH) as HadeethModel
        }
        binding.AhadeethName.text = hadeeth!!.title
        binding.AhadeethContentTv.text = hadeeth.content

    }
}