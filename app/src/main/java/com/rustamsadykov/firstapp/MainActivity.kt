package com.rustamsadykov.firstapp

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

}