package com.internship.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.internship.calculator.databinding.ActivityMainBinding
import java.lang.Exception
import kotlin.math.pow

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var isAnswered = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnDot.setOnClickListener(this)

        binding.btnAdd.setOnClickListener(this)
        binding.btnSub.setOnClickListener(this)
        binding.btnDivide.setOnClickListener(this)
        binding.btnMul.setOnClickListener(this)
        binding.btnPow.setOnClickListener(this)
        binding.btnMod.setOnClickListener(this)

        binding.btnClearAll.setOnClickListener {
            clearData()
        }

        binding.btnClear.setOnClickListener {
            if (binding.txtResult.text.isNotEmpty())
                binding.txtResult.text =
                    binding.txtResult.text.substring(0, binding.txtResult.text.length - 1)
        }

        binding.imgDaY.setOnClickListener {
            binding.root.setBackgroundColor(getColor(R.color.white))
            binding.txtResult.setTextColor(getColor(R.color.black))
            binding.txtNumbers.setTextColor(getColor(R.color.black))
        }

        binding.imgNight.setOnClickListener {
            binding.root.setBackgroundColor(getColor(R.color.bgColor))
            binding.txtResult.setTextColor(getColor(R.color.white))
            binding.txtNumbers.setTextColor(getColor(R.color.white))
        }

        binding.btnEqual.setOnClickListener {
            val expression = binding.txtResult.text
            val operators = arrayOf("+", "-", "*", "/", "%", "^")
            var operatorIndex = -1
            for (i in expression.indices) {
                val char = expression[i].toString()
                if (operators.contains(char)) {
                    operatorIndex = i
                    break
                }
            }

            try {
                val value1 = expression.substring(0, operatorIndex).toDouble()
                val operator = expression[operatorIndex].toString()
                val value2 = expression.substring(operatorIndex + 1).toDouble()

                if (operatorIndex > -1) {
                    val result = performOperation(value1, operator, value2)
                    binding.txtResult.text = result
                } else
                    binding.txtResult.text = getString(R.string.please_write_correct_syntax)
            } catch (e: Exception) {
                binding.txtResult.text = getString(R.string.error, e.message)
            }
            finally {
                binding.txtNumbers.text = expression
            }

            isAnswered = true
        }
    }

    override fun onClick(it: View?) {
        if (isAnswered)
            clearData()
        binding.txtResult.text = binding.txtResult.text.toString().plus((it as Button).text)
    }

    private fun clearData() {
        binding.txtNumbers.text = ""
        binding.txtResult.text = ""

        isAnswered = false
    }

    private fun performOperation(value1: Double, operator: String, value2: Double): String {
        return when (operator) {
            "+" -> (value1 + value2).toString()
            "-" -> (value1 - value2).toString()
            "*" -> (value1 * value2).toString()
            "/" -> {
                if (value2 != 0.0) (value1 / value2).toString()
                else getString(R.string.error_division_by_zero)
            }

            "%" -> {
                if (value2 != 0.0) (value1 % value2).toString()
                else getString(R.string.error_modulo_by_zero)
            }

            "^" -> (value1.pow(value2)).toString()
            else -> getString(R.string.error_invalid_operator)
        }
    }
}
