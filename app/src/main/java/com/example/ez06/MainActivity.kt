package com.example.ez06

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentInput: String = ""
    private var currentOperator: String? = ""
    private var firstOperand: Double? = 0.0
    private var result: Double? = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.tvInput)

        // เพิ่ม OnClickListener ให้กับปุ่มทุกปุ่มตัวเลข
        val numberButtons = arrayOf(
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8,
            R.id.btn9, R.id.btn0, R.id.btnDot
        )

        for (buttonId in numberButtons) {
            findViewById<Button>(buttonId).setOnClickListener {
                onNumberButtonClick(it)
            }
        }

        // เพิ่ม OnClickListener ให้กับปุ่มลบ
        findViewById<Button>(R.id.clear).setOnClickListener {
            onDeleteButtonClick()
        }

        // เพิ่ม OnClickListener ให้กับปุ่มลบทั้งหมด
        findViewById<Button>(R.id.allClear).setOnClickListener {
            clearAll()
        }


        // เพิ่ม OnClickListener ให้กับปุ่มเพิ่ม, ลบ, คูณ, หาร, modulo
        val operatorButtons = arrayOf(
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply,R.id.btnDivide, R.id.btnModulo
        )

        for (buttonId in operatorButtons) {
            findViewById<Button>(buttonId).setOnClickListener {
                onOperatorButtonClick(it)
            }
        }

        // เพิ่ม OnClickListener ให้กับปุ่มเท่ากับ
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            onEqualsButtonClick()
        }
    }

    private fun clearAll() {
        currentInput = "0"
        currentOperator = null
        firstOperand = null
        updateResult()
    }




    private fun onNumberButtonClick(view: View) {
        val buttonText = (view as Button).text.toString()

        if (currentInput == "0") {
            // กรณีที่เป็นเลข 0 เลขเดียวบนหน้าจอ ไม่เพิ่มจำนวนเลข 0
            currentInput = buttonText
        } else {
            currentInput += buttonText
        }

        updateResult()
    }

    private fun onDeleteButtonClick() {
        if (currentInput.length > 1) {
            // ลบตัวเลขที่พิมพ์ล่าสุดทีละ 1 หลัก
            currentInput = currentInput.substring(0, currentInput.length - 1)
        } else {
            // หากเหลือเลขหลักเดียว กดลบซ้ำเป็นเลข 0
            currentInput = "0"
        }

        updateResult()
    }

    private fun onOperatorButtonClick(view: View) {
        currentOperator = (view as Button).text.toString()
        if (firstOperand == null) {
            firstOperand = currentInput.toDouble()
        } else {
            // ถ้ามี firstOperand แล้วแสดงว่าเป็นการคำนวณต่อเนื่อง
            onEqualsButtonClick()  // ทำการคำนวณก่อนหน้ากับ currentOperator ที่กด
            currentOperator = (view as Button).text.toString()
        }
        currentInput = "0"
    }



    private fun onEqualsButtonClick() {
        if (currentOperator != null && firstOperand != null) {
            val secondOperand = currentInput.toDouble()
            val result = when (currentOperator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "X" -> firstOperand!! * secondOperand
                "/" -> firstOperand!! / secondOperand
                "%" -> firstOperand!! % secondOperand
                else -> throw IllegalArgumentException("Invalid operator")
            }

            currentInput = result.toString()
            firstOperand =  result

            updateResult()
        }
    }

    private fun updateResult() {
        resultTextView.text = currentInput
    }
}