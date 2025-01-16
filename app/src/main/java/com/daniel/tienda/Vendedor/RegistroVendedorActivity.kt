package com.daniel.tienda.Vendedor
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daniel.tienda.Constantes
import com.daniel.tienda.R
import com.daniel.tienda.databinding.ActivityRegistroVendedorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.intellij.lang.annotations.Pattern

class RegistroVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrarV.setOnClickListener{
            validarInformacion()
        }
    }

    private  var nombres = ""
    private  var email = ""
    private  var password = ""
    private  var  cpassword = ""
    private fun validarInformacion() {
        nombres = binding.etNombresV.text.toString().trim()
        email = binding.etEmailV.text.toString().trim()
        password = binding.etPasswordV.text.toString().trim()
        cpassword = binding.etCPasswordV.text.toString().trim()

        if (nombres.isEmpty()){
            binding.etNombresV.error = "ingrese su nombre"
            binding.etNombresV.requestFocus()
        } else if (email.isEmpty()){
            binding.etEmailV.error = "ingrese su email"
            binding.etEmailV.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailV.error = "Email no valido"
            binding.etEmailV.requestFocus()
        } else if (password.isEmpty()){
            binding.etPasswordV.error = "ingrese password"
            binding.etPasswordV.requestFocus()
        } else if (password.length <=6){
            binding.etPasswordV.error = "Necesita 6 o mas car"
            binding.etPasswordV.requestFocus()
        } else if (cpassword.isEmpty()){
            binding.etCPasswordV.error = "confirme password"
            binding.etPasswordV.requestFocus()
        } else if (password!=cpassword){
            binding.etCPasswordV.error = "no coincide"
            binding.etCPasswordV.requestFocus()
        } else{
            registrarVendedor()
        }
    }

    private fun registrarVendedor() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                insertarInfoBD()
            }
            .addOnFailureListener { e->
                Toast.makeText(this, "Fallo el registro debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun insertarInfoBD() {
        progressDialog.setMessage("Guardando información...")

        val uidBD = firebaseAuth.uid
        val nombreBD = nombres
        val  emailBD = email
        val  tipoUsuario = "vendedor"
        val tiempoBD = Constantes().obtenerTiempoD()

        val datosVendedor = HashMap<String, Any>()

        datosVendedor["uid"] = "$uidBD"
        datosVendedor ["nombres"] ="$nombreBD"
        datosVendedor ["email"] = "$emailBD"
        datosVendedor["tipoUsuario"] = "vendedor"
        datosVendedor ["tiempo_registro"] = tiempoBD

        val  references = FirebaseDatabase.getInstance().getReference("Usuarios")
        references.child(uidBD!!)
            .setValue(datosVendedor)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityVendedor::class.java))
                finish()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "Fallo el registro BD debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}