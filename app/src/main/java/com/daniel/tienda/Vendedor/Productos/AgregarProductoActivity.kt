package com.daniel.tienda.Vendedor.Productos

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.daniel.tienda.R
import com.daniel.tienda.databinding.ActivityAgregarProducto2Binding
import com.github.dhaval2404.imagepicker.ImagePicker

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarProducto2Binding
    private var imagenUri : Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarProducto2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgAgregarProducto.setOnClickListener {
            seleccionarImg()
        }
    }
    private fun seleccionarImg(){
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080,1080)
            .createIntent { intent->
             resultadoImg.launch(intent)

            }

    }
    private val resultadoImg =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado ->
            if (resultado.resultCode== Activity.RESULT_OK){
                val data = resultado.data
                imagenUri= data!!.data
                binding.imgAgregarProducto.setImageURI(imagenUri)
            }else{
                Toast.makeText(this,"Accion cancelada",Toast.LENGTH_SHORT).show()

            }
        }
}