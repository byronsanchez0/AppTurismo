package com.example.fasedecaetdra2

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fasedecaetdra2.databinding.ActivityMainBinding
import com.example.taller2.repository.SiteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buildList()
        addListeners()


    }

    private fun buildList() {

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101)

        val repository = SiteRepository.getRepository(this)

        val layoutManager = GridLayoutManager(this, 1)

        lifecycleScope.launch {
            repository.allSite.collect { sites ->
                binding.newSite.apply {
                    adapter = SiteAdapter(sites, this@MainActivity)
                    setLayoutManager(layoutManager)
                }
            }
        }
    }


    private fun addListeners() {
        binding.btnAddplace.setOnClickListener {
            startActivity(Intent(this, AddSiteActivity::class.java))

        }

        binding.btnWb.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))


        }


    }
}
