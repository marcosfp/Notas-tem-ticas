package com.example.notastematicas.rest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notastematicas.R
import com.example.notastematicas.databinding.ItemDogBinding

class DogsAdapter() : RecyclerView.Adapter<DogsAdapter.ViewHolder>() {

    private var images = ArrayList<String>()


    fun setDogList(dog: DogsResponse) {
        this.images = dog.images as ArrayList<String>
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemDogBinding.bind(view)

        fun bind(image: String) {
            binding.ivDog.fromUrl(image)
        }
    }
}

