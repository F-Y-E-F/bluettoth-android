package com.example.tictactoearduino

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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
        setActivePlayerText(activePlayer)
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
        showWinner(findWinner())

        Log.d("TAG",listOfOFields.toString())
        Log.d("TAG",listOfXFields.toString())
    }

    private fun playerContains(firstField:Int,secondField:Int,thirdField:Int): ArrayList<Boolean>  =  arrayListOf(
            listOfOFields.contains(firstField) && listOfOFields.contains(secondField) && listOfOFields.contains(thirdField),
            listOfXFields.contains(firstField) && listOfXFields.contains(secondField) && listOfXFields.contains(thirdField))



    private fun setActivePlayerText(playerSymbol:String){
        val str = SpannableStringBuilder("It's $playerSymbol move")
        str.setSpan(StyleSpan(R.font.segoeuib), 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        playerMoveText.text = str
    }

    private fun findWinner():Int{
        var winner:Int = -1

        when {
            playerContains(0,1,2)[0] -> winner = 1
            playerContains(0,1,2)[1] -> winner = 2
            playerContains(3,4,5)[0] -> winner = 1
            playerContains(3,4,5)[1] -> winner = 2
            playerContains(6,7,8)[0] -> winner = 1
            playerContains(6,7,8)[1] -> winner = 2
            playerContains(0,3,6)[0] -> winner = 1
            playerContains(0,3,6)[1] -> winner = 2
            playerContains(1,4,7)[0] -> winner = 1
            playerContains(1,4,7)[1] -> winner = 2
            playerContains(2,5,8)[0] -> winner = 1
            playerContains(2,5,8)[1] -> winner = 2
            playerContains(0,4,8)[0] -> winner = 1
            playerContains(0,4,8)[1] -> winner = 2
            playerContains(2,4,6)[0] -> winner = 1
            playerContains(2,4,6)[1] -> winner = 2
            listOfOFields.size + listOfXFields.size == 9 -> winner = 0
        }

        return winner
    }

    private fun showWinner(winner:Int){
        if(winner!=-1) listOfButtons.forEach {
            it.isEnabled = false
        }
        when(winner){
            0-> Snackbar.make(mainView,"Draw",Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#ffd500")).show()
            1-> Snackbar.make(mainView,"o player win!",Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#ffd500")).show()
            2-> Snackbar.make(mainView,"x player win!",Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#ffd500")).show()
        }

    }
}