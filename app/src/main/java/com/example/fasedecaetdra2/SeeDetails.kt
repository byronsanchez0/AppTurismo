package com.example.fasedecaetdra2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fasedecaetdra2.databinding.ActivityMainBinding
import com.example.fasedecaetdra2.databinding.ActivitySeeDetailsBinding
import com.example.taller2.entities.Site
import com.example.taller2.repository.SiteRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SeeDetails : AppCompatActivity() {

    private lateinit var binding: ActivitySeeDetailsBinding
    private var site : Site? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        val idsite = intent.getIntExtra("id", 0)

        val repository = SiteRepository.getRepository(this)

        lifecycleScope.launch {
            repository.allSite.collect { sites ->

                sites.forEach {

                    if (it.id == idsite) {
                        binding.tvSite.text = it.name
                        binding.tvExpe.text = it.experience
                        binding.tvUbi.text = it.direction


                    }

                    binding.btnActualizar.setOnClickListener {

                        val intent = Intent(this@SeeDetails, UpdateSite::class.java)
                        intent.putExtra("id", idsite )
                        startActivity(intent)


                    }




                }

            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}