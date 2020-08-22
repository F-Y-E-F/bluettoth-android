package com.example.tictactoearduino

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_play_game.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class PlayGame : AppCompatActivity() {


    companion object{
        var uid :UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var bluetoothSocket : BluetoothSocket? = null
        lateinit var bluetoothAdapter: BluetoothAdapter
        lateinit var address:String
    }

    //list of all buttons
    private lateinit var listOfButtons : ArrayList<ImageView>
    //active player symbol
    private var activePlayer:String = "o"


    //list of fields x and o player
    private var listOfOFields = ArrayList<Int>()
    private var listOfXFields = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        //get address from intent
        address = if(intent.hasExtra(MainActivity.EXTRA_ADDRESS)) intent.getStringExtra(MainActivity.EXTRA_ADDRESS)!! else ""
        //execute async task to connect
        ConnectToDevice(this).execute()

        listOfButtons  = arrayListOf(field_1,field_2,field_3,field_4,field_5,field_6,field_7,field_8,field_9)
        setRandomStartPlayer()
        buttonsOnClick()

    }




    //set text which player turn is it
    private fun setActivePlayerText(playerSymbol:String){
        val str = SpannableStringBuilder("It's $playerSymbol move")
        str.setSpan(StyleSpan(R.font.segoeuib), 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        playerMoveText.text = str
    }



    //generate random player on start
    private fun setRandomStartPlayer(){
        activePlayer = if (Random.nextInt(0,1) == 0) "o" else "x"
        setActivePlayerText(activePlayer)
    }


    //-------------------------set buttons on clicks-------------------------------
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
    //===========================================================================

    //set fields symbol(x or o) and check the winner
    private fun setFieldSymbol(fieldId: Int) {
        listOfButtons[fieldId].setImageResource(if(activePlayer=="o")R.drawable.tic else R.drawable.tac)
        checkWinner()
    }


   //checkwinner
    private fun checkWinner() {
        showWinner(findWinner())
        Log.d("TAG",listOfOFields.toString())
        Log.d("TAG",listOfXFields.toString())
    }

    //---------------------check if players lists contains all of field numbers-------------------------------
    private fun playerContains(firstField:Int,secondField:Int,thirdField:Int): ArrayList<Boolean>  =  arrayListOf(
            listOfOFields.contains(firstField) && listOfOFields.contains(secondField) && listOfOFields.contains(thirdField),
            listOfXFields.contains(firstField) && listOfXFields.contains(secondField) && listOfXFields.contains(thirdField))






    //--------------------------------find winner by contains numbers in player list----------------------------------------
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
    //=========================================================================================================================

    //---------------------------------show winner on snackbar and go back---------------------------------
    private fun showWinner(winner:Int){
        if(winner!=-1) listOfButtons.forEach {
            it.isEnabled = false
        }
        when(winner){
            0-> Snackbar.make(mainView,"Draw",Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#ffd500")).show()
            1-> Snackbar.make(mainView,"o player win!",Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#ffd500")).show()
            2-> Snackbar.make(mainView,"x player win!",Snackbar.LENGTH_LONG).setTextColor(Color.parseColor("#ffd500")).show()
        }
        if(winner != -1) Handler().postDelayed({onBackPressed()},4000)
    }
    //=======================================================================================



    //--------------------------send message to device------------------------------
    private fun setCommand(input: String){
        if(bluetoothSocket !=null){
            try{
                bluetoothSocket!!.outputStream.write(input.toByteArray())
            }catch (e:IOException){
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
    //==============================================================================


    //-----------------------------------disconnect from device----------------------------------
    private fun disconnect(){
        if(bluetoothSocket!=null){
            try {
                bluetoothSocket!!.close()
                bluetoothSocket = null
            }catch (e:IOException){e.printStackTrace()}
        }
        finish()
    }
    //============================================================================================
}