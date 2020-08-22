package com.example.tictactoearduino

import android.graphics.Typeface
import android.icu.lang.UProperty.INT_START
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_play_game.*
import kotlin.random.Random


class PlayGame : AppCompatActivity() {

    private lateinit var listOfButtons : ArrayList<ImageView>
    private var activePlayer:String = "o"


    private var listOfOFields = ArrayList<Int>()
    private var listOfXFields = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)
        listOfButtons  = arrayListOf(field_1,field_2,field_3,field_4,field_5,field_6,field_7,field_8,field_9)
        setRandomStartPlayer()
        buttonsOnClick()
    }


    private fun setRandomStartPlayer(){
        activePlayer = if (Random.nextInt(0,1) == 0) "o" else "x"
    }


    private fun buttonsOnClick(){
        var fieldId:Int
        listOfButtons.forEach { field->
            field.setOnClickListener {
                fieldId = listOfButtons.indexOf(field)
                if(activePlayer=="o") listOfOFields.add(fieldId) else listOfXFields.add(fieldId)
                setActivePlayerText(if(activePlayer=="o")"x" else "o")
                setFieldSymbol(fieldId)
                activePlayer = if(activePlayer == "o") "x" else "o"
                listOfButtons[fieldId].isEnabled = false
            }
        }
    }

    private fun setFieldSymbol(fieldId: Int) {
        listOfButtons[fieldId].setImageResource(if(activePlayer=="o")R.drawable.tic else R.drawable.tac)
        checkWinner()
    }

    private fun checkWinner() {
        var winner:Int = -1


        for(i:Int in 1..3){
                
        }


        Log.d("TAG",listOfOFields.toString())
        Log.d("TAG",listOfXFields.toString())
    }


    private fun setActivePlayerText(playerSymbol:String){
        val str = SpannableStringBuilder("It's $playerSymbol move")
        str.setSpan(StyleSpan(R.font.segoeuib), 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        playerMoveText.text = str
    }
}