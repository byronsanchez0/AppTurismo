package com.example.fasedecaetdra2

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fasedecaetdra2.databinding.ActivityAddSiteBinding

import com.example.taller2.entities.Site

import com.example.taller2.repository.SiteRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


const val AUTHORITY = "com.example.fasedecaetdra2.fileprovider"
const val SUFFIX = ".jpg"

class AddSiteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSiteBinding
    private lateinit var imagenUrl : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSiteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addListener()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.data != null) {
                        var url = result.data?.data
                        binding.ivPhoto.setImageURI(result.data?.data)
                        binding.ivPhoto.rotation = 0f

                        println("FOO00000000000000000000OOTOOOO: $url ")

                        imagenUrl = url.toString()


                    }
                }
            }

        binding.btnGellery.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            resultLauncher.launch(gallery)
        }


    }

    private fun addListener() {
        val repository = SiteRepository.getRepository(this)
        binding.btnAdd.setOnClickListener {
            hideKeyboard()
            with(binding) {
                if (cajaNom.text.isBlank() ) {
                    Snackbar.make(this.root, "Some fields are empty", Snackbar.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            repository.insert(
                                Site(
                                    name = cajaNom.text.toString(),
                                    direction = cajaUbi.text.toString(),
                                    experience = cajaExp.text.toString(),
                                    urlImagen = imagenUrl
                                )
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}