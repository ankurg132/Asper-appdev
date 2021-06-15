package com.semicolon.goodcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val change_mode_btn  = findViewById<Button>(R.id.change_mode_btn1)
            checkTheme()
            change_mode_btn.setOnClickListener { chooseThemeDialog() }

            val tvOne = findViewById<TextView>(R.id.tvOne)
            val tvTwo = findViewById<TextView>(R.id.tvTwo)
            val tvThree = findViewById<TextView>(R.id.tvThree)
            val tvFour = findViewById<TextView>(R.id.tvFour)
            val tvFive = findViewById<TextView>(R.id.tvFive)
            val tvSix = findViewById<TextView>(R.id.tvSix)
            val tvSeven = findViewById<TextView>(R.id.tvSeven)
            val tvEight = findViewById<TextView>(R.id.tvEight)
            val tvNine = findViewById<TextView>(R.id.tvNine)
            val tvZero = findViewById<TextView>(R.id.tvZero)
            val tvExpression = findViewById<TextView>(R.id.tvExpression)
            val tvResult = findViewById<TextView>(R.id.tvResult)
            val tvPlus = findViewById<TextView>(R.id.tvPlus)
            val tvMinus = findViewById<TextView>(R.id.tvMinus)
            val tvMul = findViewById<TextView>(R.id.tvMul)
            val tvDivide = findViewById<TextView>(R.id.tvDivide)
            val tvOpen = findViewById<TextView>(R.id.tvOpen)
            val tvClose = findViewById<TextView>(R.id.tvClose)
            val tvClear = findViewById<TextView>(R.id.tvClear)
            val tvDot = findViewById<TextView>(R.id.tvDot)
            val tvBack = findViewById<TextView>(R.id.tvbackspace)
            val tvEquals = findViewById<TextView>(R.id.tvEquals)

            //Numbers
            tvOne.setOnClickListener { appendOnExpression("1", true)}
            tvTwo.setOnClickListener { appendOnExpression("2", true) }
            tvThree.setOnClickListener { appendOnExpression("3", true) }
            tvFour.setOnClickListener { appendOnExpression("4", true) }
            tvFive.setOnClickListener { appendOnExpression("5", true) }
            tvSix.setOnClickListener { appendOnExpression("6", true)}
            tvSeven.setOnClickListener { appendOnExpression("7", true)}
            tvEight.setOnClickListener { appendOnExpression("8", true)}
            tvNine.setOnClickListener { appendOnExpression("9", true) }
            tvZero.setOnClickListener { appendOnExpression("0", true) }
            tvDot.setOnClickListener { appendOnExpression(".", true) }

            //Operators
            tvPlus.setOnClickListener { appendOnExpression("+", false) }
            tvMinus.setOnClickListener { appendOnExpression("-", false) }
            tvMul.setOnClickListener { appendOnExpression("*", false) }
            tvDivide.setOnClickListener { appendOnExpression("/", false) }
            tvOpen.setOnClickListener { appendOnExpression("(", false)}
            tvClose.setOnClickListener { appendOnExpression(")", false) }

            tvClear.setOnClickListener {
                tvExpression.text = ""
                tvResult.text = ""
            }

            tvBack.setOnClickListener {
                val string = tvExpression.text.toString()
                if (string.isNotEmpty()) {
                    tvExpression.text = string.substring(0, string.length - 1)
                }
                tvResult.text = ""
            }

            tvEquals.setOnClickListener {
                try {

                    val expression = ExpressionBuilder(tvExpression.text.toString()).build()
                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    if (result == longResult.toDouble())
                        tvResult.text = longResult.toString()
                    else
                        tvResult.text = result.toString()

                } catch (e: Exception) {
                    Log.d("Exception", " message : " + e.message)
                }
            }
        }

    //override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
          //  menuInflater.inflate(R.menu.menu, menu)
           // return true
        //}


    private fun appendOnExpression(string: String, canClear: Boolean) {
        val tvExpression = findViewById<TextView>(R.id.tvExpression)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        if(tvResult.text.isNotEmpty()){
            tvExpression.text = ""
        }

        if (canClear) {
            tvResult.text = ""
            tvExpression.append(string)
        } else {
            tvExpression.append(tvResult.text)
            tvExpression.append(string)
            tvResult.text = ""
        }

    }
    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.theme))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }
    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }
}



