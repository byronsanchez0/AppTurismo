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
import java.util.regex.Pattern


const val AUTHORITY = "com.example.fasedecaetdra2.fileprovider"
const val SUFFIX = ".jpg"

class AddSiteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSiteBinding
    private var imagenUrl : String = ""



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
                //VALIDACION DE LA CAJA NOMBRE
                val name = binding.cajaNom.text
                val isvalid = Pattern.compile("^[A-Z][a-zA-ZÀ-ÿ\\u00f1\\u00d1,.!¿?#()+= \\s]+$").matcher(name).matches()
                if (cajaNom.text.isNotBlank()) {
                    if(isvalid){
                        //VALIDACION DE LA CAJA UBICACION
                        if(cajaUbi.text.isNotBlank()) {
                            val ubi = binding.cajaUbi.text
                            val isvalid =
                                Pattern.compile("[a-zA-ZÀ-ÿ0-9\\u00f1\\u00d1,.!¿?#()+= \\s]{0,50}\$").matcher(ubi).matches()
                            if (isvalid) {
                                //VALIDACION DE LA CAJA EXPERIENCIA
                                val exp = binding.cajaExp.text
                                val isvalid = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1,.!¿?#()+=\\s ]{0,150}\$").matcher(exp).matches()

                                if(imagenUrl.isBlank()){

                                    Snackbar.make(this.root, "Some fields are empty", Snackbar.LENGTH_SHORT).show()

                                }else if(cajaExp.text.isNotBlank()) {
                                    if (isvalid) {
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
                                            onBackPressed()
                                        }
                                    } else {

                                        binding.cajaExp.error ="1) Este campo solamente acepta letras\r" +
                                                "2) Maximo de 150 caracteres"
                                    }
                                }else{
                                    binding.cajaExp.error = "Campo vacio"
                                }
                                //FIN DE VALIDACION CAJA EXPERIENCIA
                            } else {

                                binding.cajaUbi.error ="Se ha sobrepasado el limite de 50 caracteres"
                            }
                        }else{
                            binding.cajaUbi.error = "Campo vacio"
                        }
                        //FIN DE LA VALIDACION DE LA CAJA UBICACION
                    }else {

                        binding.cajaNom.error = "1) Este campo solamente acepta letras\r" +
                                "2) La primera letra debe ser mayuscula"
                    }

                } else {
                    binding.cajaNom.error ="Campo vacio"
                }
                //FIN DE VALIDACION DE CAJA NOMBRE
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