package com.example.fasedecaetdra2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fasedecaetdra2.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {


    private val wb_url= "https://www.trivago.es/?aDateRange%5Barr%5D=2021-10-20&aDateRange%5Bdep%5D=2021-10-21&aPriceRange%5Bfrom%5D=0&aPriceRange%5Bto%5D=0&iRoomType=7&aRooms%5B0%5D%5Badults%5D=2&cpt2=174%2F200&hasList=1&hasMap=1&bIsSeoPage=0&sortingId=1&slideoutsPageItemId=&iGeoDistanceLimit=20000&address=&addressGeoCode=&offset=0&ra=&overlayMode="
    lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings= binding.wbTravel.settings
        settings.javaScriptEnabled = true
        binding.wbTravel.loadUrl(wb_url)

    }

    override fun onBackPressed() {
        if(binding.wbTravel.canGoBack()){
            binding.wbTravel.goBack()
        }else{
            super.onBackPressed()
        }
    }

}