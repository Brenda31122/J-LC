package com.daniel.tienda.Vendedor.Nav_Fragments_Vendedor

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daniel.tienda.R
import com.daniel.tienda.databinding.FragmentCategoriasVBinding
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase


class FragmentCategoriasV : Fragment() {
    private lateinit var binding:FragmentCategoriasVBinding
    private lateinit var mContext : Context
    private lateinit var progressDialog : ProgressDialog

    override fun onAttach(context: Context) {
        mContext = context

        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentCategoriasVBinding.inflate(inflater,container,false)

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Espere por favor...")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnAgregarCat.setOnClickListener{
            validarInfo()
        }

        return binding.root
    }
    private var categoría = ""
    private fun validarInfo() {
     categoría = binding.etCategoria.text.toString().trim()
        if (categoría.isEmpty()){
            Toast.makeText(context,"Ingrese catergoria",Toast.LENGTH_SHORT).show()
        }else{
            agregarCatBD()
        }
    }

    private fun agregarCatBD() {
        progressDialog.setMessage("Agregando Categorias..")
        progressDialog.show()

        val ref = FirebaseDatabase.getInstance().getReference("Categorias")
        val keyId = ref.push().key

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "${keyId}"
        hashMap["categoria"] = "${categoría}"

        ref.child(keyId!!)
            .setValue(hashMap)
            .addOnSuccessListener{
                progressDialog.dismiss()
                Toast.makeText(context,"Se agrego Categoria con Exito",Toast.LENGTH_SHORT).show()
                binding.etCategoria.setText("")

            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(context,"${e.message}",Toast.LENGTH_SHORT).show()

            }















    }


}