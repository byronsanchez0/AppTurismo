package com.example.fasedecaetdra2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import com.example.fasedecaetdra2.databinding.ActivitySeeDetailsBinding
import com.example.fasedecaetdra2.databinding.ActivityUpdateSiteBinding
import com.example.taller2.entities.Site
import com.example.taller2.repository.SiteRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateSite : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateSiteBinding
    private var variablechingona = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUpdateSiteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        var idsite = intent.getIntExtra("id", 0)

        variablechingona = idsite

        val repository = SiteRepository.getRepository(this)

        lifecycleScope.launch {
            repository.allSite.collect { sites ->

                sites.forEach {

                    if (it.id == idsite) {

                        binding.cajaNom.setText(it.name)
                        binding.cajaUbi.setText(it.direction)
                        binding.cajaExp.setText(it.experience)

                        UpdateListener()


                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun UpdateListener() {
        val repository = SiteRepository.getRepository(this)
        binding.btnUpdate.setOnClickListener {
            hideKeyboard()
            with(binding) {
                if (cajaNom.text.isBlank()) {
                    Snackbar.make(this.root, "Some fields are empty", Snackbar.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            repository.updateItem(
                                Site(
                                    id = variablechingona,
                                    name = cajaNom.text.toString(),
                                    direction = cajaUbi.text.toString(),
                                    experience = cajaExp.text.toString(),
                                    urlImagen = ivPhoto.toString()                                )
                            )
                        }
                        super.onBackPressed()
                    }
                }
            }
        }
    }


    private fun hideKeyboard() {
        val manager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

}