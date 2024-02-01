package com.noureldin.holyquran.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.noureldin.holyquran.data.Constant
import com.noureldin.holyquran.databinding.ActivitySuraDetailsBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class SuraDetailsActivity : AppCompatActivity() {

    lateinit var Binding: ActivitySuraDetailsBinding

    lateinit var suraName: String
    lateinit var fileName: String
    var AyatCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding = ActivitySuraDetailsBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        suraName = intent.getStringExtra(Constant.SURA_NAME)!!
        fileName = intent.getStringExtra(Constant.FILE_NAME)!!
        AyatCount = intent.getIntExtra(Constant.AyatCount, 0)


        Binding.contentTv.text = readFileContent()
        Binding.chapterName.text = suraName
        Binding.chapterNumbersAya.text = AyatCount.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        Log.e(
            "ActivitySuraDetailsBinding/onCreate",
            "suraName =${suraName}" + "+ file = ${fileName}" + "numberOfAyat =${AyatCount}"
        )


    }

    private fun readFileContent(): String {
        var fileContent = ""
        try {
            val inputStream = assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val fileLines: List<String> = reader.readLines()
            fileContent = fileLines.joinToString(separator = "") { line ->
                val index = fileLines.indexOf(line) + 1

                return@joinToString line + "{${index}}"
            }

            Log.e("SuraDetailsActivity/readFileContent", "fileLines = ${fileContent}")
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return fileContent

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle the Up button click
                onBackPressed()
                return true
            }
            // Add other menu item cases if needed
            else -> return super.onOptionsItemSelected(item)
        }

    }
}