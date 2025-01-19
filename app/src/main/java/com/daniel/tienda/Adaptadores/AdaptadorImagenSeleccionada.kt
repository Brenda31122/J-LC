package com.daniel.tienda.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.daniel.tienda.Modelos.ModeloImagenSeleccionada
import com.daniel.tienda.databinding.ItemImagenesSeleccionadasBinding


class AdaptadorImagenSeleccionada (
    private val context : Context,
    private val imagenesSelecArrayList : ArrayList<ModeloImagenSeleccionada>
): Adapter<AdaptadorImagenSeleccionada.HolderImagenSeleccionada>()
{
    private lateinit var binding: ItemImagenesSeleccionadasBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImagenSeleccionada {
        binding = ItemImagenesSeleccionadasBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderImagenSeleccionada(binding.root)
    }
    override fun getItemCount(): Int {
        return imagenesSelecArrayList.size

    }

    override fun onBindViewHolder(holder: HolderImagenSeleccionada, position: Int) {
        val modelo = imagenesSelecArrayList[position]

        val imagenUri = modelo.imageUri
    }
    inner class HolderImagenSeleccionada (itemView : View) : ViewHolder(itemView){
      var item_imagen = binding.itemImagen
      var btn_borrar = binding.borrarItem


  }

}