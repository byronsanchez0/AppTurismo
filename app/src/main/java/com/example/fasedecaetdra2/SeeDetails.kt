package com.example.fasedecaetdra2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

                        var url = it.urlImagen
                        println("prueba: $url")


                        if (ContextCompat.checkSelfPermission(this@SeeDetails,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                            // Permission is not granted
                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(this@SeeDetails,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                // Show an explanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.
                            } else {
                                // No explanation needed; request the permission
                                ActivityCompat.requestPermissions(this@SeeDetails, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101)

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback mmethod gets the
                                // result of the request.
                            }
                        } else {
                            // Permission has already been granted
                        }

                        binding.ivPhoto.setImageURI(Uri.parse(url))
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