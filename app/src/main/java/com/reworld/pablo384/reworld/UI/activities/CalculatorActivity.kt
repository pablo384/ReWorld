package com.reworld.pablo384.reworld.UI.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.reworld.pablo384.reworld.R
import kotlinx.android.synthetic.main.activity_calculator.*

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        getButtonIds().forEach {
            it.setOnClickListener { numpadClicked(it as Button) }
        }
        btn_clear.setOnClickListener {
            formula.text=""
            result.text=""
        }
    }

    private fun numpadClicked(id: Button) {
        formula.text="${formula.text}${id.text}"
        result.text="${(formula.text.toString().toDouble())/200}"
    }

    private fun getButtonIds() = arrayOf(btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)
}
