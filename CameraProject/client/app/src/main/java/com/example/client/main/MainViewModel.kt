package com.example.client.main

import android.Manifest
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
	val liveData = MutableLiveData<MainModel>()
}