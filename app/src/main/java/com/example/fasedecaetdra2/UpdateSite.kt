package com.example.fasedecaetdra2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
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
import java.util.regex.Pattern


class UpdateSite : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateSiteBinding
    private var variablechingona = 0
    private lateinit var imagenUrl : String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUpdateSiteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        var idsite = intent.getIntExtra("id", 0)

        variablechingona = idsite

        val repository = SiteRepository.getRepository(this)

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


        lifecycleScope.launch {
            repository.allSite.collect { sites ->

                sites.forEach {

                    if (it.id == idsite) {

                        var url = it.urlImagen

                        binding.cajaNom.setText(it.name)
                        binding.cajaUbi.setText(it.direction)
                        binding.cajaExp.setText(it.experience)
                        binding.ivPhoto.setImageURI(Uri.parse(url))

                        UpdateListener()


                    }
                }
            }
        }
    }

    private fun UpdateListener() {
        val repository = SiteRepository.getRepository(this)
        binding.btnUpdate.setOnClickListener {
            hideKeyboard()
            with(binding) {
                //VALIDACION DE LA CAJA NOMBRE
                val name = binding.cajaNom.text
                val isvalid = Pattern.compile("^[A-Z][a-z]+[a-zA-Z\\s]+$").matcher(name).matches()
                if (cajaNom.text.isNotBlank()) {
                    if (isvalid) {
                        //VALIDACION DE LA CAJA UBICACION
                        if (cajaUbi.text.isNotBlank()) {
                            val ubi = binding.cajaUbi.text
                            val isvalid = Pattern.compile("^[a-zA-Z0-9\\s]{0,20}\$").matcher(ubi).matches()
                            if (isvalid) {
                                //VALIDACION DE LA CAJA EXPERIENCIA
                                val exp = binding.cajaExp.text
                                val isvalid = Pattern.compile("^[a-zA-Z\\s]{0,40}\$").matcher(exp).matches()
                                if (cajaExp.text.isNotBlank()) {
                                    if (isvalid) {
                                        lifecycleScope.launch {
                                            withContext(Dispatchers.IO) {
                                                repository.updateItem(
                                                    Site(
                                                        id = variablechingona,
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

                                        binding.cajaExp.error = "Este campo solamente acepta letras y un maximo de 40 caracteres"
                                    }
                                } else {
                                    binding.cajaExp.error = "Campo vacio"
                                }
                                //FIN DE VALIDACION CAJA EXPERIENCIA
                            } else {

                                binding.cajaUbi.error =
                                    "Se ha sobrepasado el limite de 20 caracteres"
                            }
                        } else {
                            binding.cajaUbi.error = "Campo vacio"
                        }
                        //FIN DE LA VALIDACION DE LA CAJA UBICACION
                    } else {

                        binding.cajaNom.error =
                            "Este campo solamente acepta letras, la primera letra debe ser mayuscula"
                    }

                } else {
                    binding.cajaNom.error = "Campo vacio"
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