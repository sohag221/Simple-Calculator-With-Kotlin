package com.example.simplecalculatorwithkotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simplecalculatorwithkotlin.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    var stateError = false
    var lastNumeric =false
    var lastDot=false

    private lateinit var expression:Expression




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }




    fun onClearClick(view: View) {

        binding.dataTv.text =""
        lastNumeric = false

    }
    fun onBackClick(view: View) {

        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar = binding.dataTv.text.toString().last()

            if (lastChar.isDigit()){
                onEqual()
            }
        }catch (e :Exception){

            binding.resultTv.text =""
            binding.resultTv.visibility = View.GONE
            Log.e("last chat error",e.toString())
        }

    }
    fun onOperatorClick(view: View) {

        if (!stateError && lastNumeric){
            binding.dataTv.append((view as Button).text)
            lastNumeric = false
            lastDot = false
            onEqual()
        }




    }
    fun onDigitclick(view: View) {


        if (stateError){
            binding .dataTv.text =(view as Button).text
            stateError = false
        }else{
            binding.dataTv.append((view as Button).text)
        }

        lastNumeric = true
        onEqual()


    }



    fun onAllClearClick(view: View) {

        binding.dataTv.text =""
        binding.resultTv.text =""
        stateError = false
        lastNumeric = false
        lastDot = false
        binding.resultTv.visibility=View.GONE


    }
    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTv.text= binding.resultTv.text.toString().drop(1)

    }


    fun onEqual(){
        if (lastNumeric && !stateError){

            val txt=binding.dataTv.text.toString()

            expression = ExpressionBuilder(txt).build()

            try {
                val result=expression.evaluate()
                binding.resultTv.visibility=View.VISIBLE
                binding.resultTv.text= "="+result.toString()
            }
            catch (ex : ArithmeticException){
                Log.e("evaluate error",ex.toString())
                binding.resultTv.text="Error"
                stateError = true
                lastNumeric = false
            }

        }

    }

}