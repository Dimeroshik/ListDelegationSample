package com.example.listdelegationsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//* В MainActivity ничего нет потому что мы попадаем в ListFragment согласно
// навигационному графу, с ним вы уже познакомились ранее
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}