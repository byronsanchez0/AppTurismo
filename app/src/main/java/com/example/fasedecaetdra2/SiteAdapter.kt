package com.example.fasedecaetdra2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fasedecaetdra2.databinding.ItemTripiBinding

import com.example.taller2.entities.Site

import com.example.taller2.repository.SiteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("MemberVisibilityCanBePrivate")
class SiteAdapter (private val list: List<Site>, private val context: Context) :

    RecyclerView.Adapter<SiteAdapter.SitesViewHolder>() {

    class SitesViewHolder(val binding: ItemTripiBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SitesViewHolder {
        val binding = ItemTripiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SitesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SitesViewHolder, position: Int) {
        with(holder.binding) {
            tvSite.text = list[position].name

            btnDelete.setOnClickListener {
                val repository = SiteRepository.getRepository(context)
                CoroutineScope(Dispatchers.IO).launch {
                    repository.deleteOneItem(list[position].id)

                }
            }

        }
    }




    override fun getItemCount(): Int = list.size
}
