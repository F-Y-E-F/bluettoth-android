package com.example.tictactoearduino

import android.graphics.Typeface
import android.icu.lang.UProperty.INT_START
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_play_game.*


class PlayGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)
        val str = SpannableStringBuilder("It's  x  move")
        str.setSpan(
            StyleSpan(R.font.segoeuib),
            4,
            8,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        playerMoveText.text = str
    }
}