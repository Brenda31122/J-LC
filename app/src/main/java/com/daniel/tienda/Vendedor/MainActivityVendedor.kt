package com.daniel.tienda.Vendedor

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.daniel.tienda.R
import com.daniel.tienda.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductosV
import com.daniel.tienda.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.daniel.tienda.Vendedor.Nav_Fragments_Vendedor.FragmentCategoriasV
import com.daniel.tienda.Vendedor.Nav_Fragments_Vendedor.FragmentMiTiendaV
import com.daniel.tienda.Vendedor.Nav_Fragments_Vendedor.FragmentReseniasV
import com.daniel.tienda.Vendedor.Nav_Fragments_Vendedor.FragmentinicioV
import com.daniel.tienda.databinding.ActivityMainVendedorBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivityVendedor : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainVendedorBinding
    private  var firebaseAuth : FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(FragmentinicioV())
        binding.navigationView.setCheckedItem(R.id.op_inicio_v)
    }

    private fun cerrarSesion(){
        firebaseAuth!!.signOut()
        startActivity(Intent(applicationContext, LoginVendedorActivity::class.java))
        finish()
        Toast.makeText(applicationContext, "has cerrado sesión", Toast.LENGTH_SHORT).show()
    }


    private fun comprobarSesion() {
        /*si el usuario no ha iniciado sesion*/
        if (firebaseAuth!!.currentUser==null){
            startActivity(Intent(applicationContext, LoginVendedorActivity::class.java))
            Toast.makeText(applicationContext, "vendedor no registrado o no logeado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "vendedor en linea", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op_inicio_v -> {
                replaceFragment(FragmentinicioV())
            }
            R.id.op_mi_tienda_v -> {
                replaceFragment(FragmentMiTiendaV())
            }
            R.id.op_categorias_v->{
                replaceFragment(FragmentCategoriasV())
            }
            R.id.op_resenia_v -> {
                replaceFragment(FragmentReseniasV())
            }
            R.id.op_cerrar_sesion_v -> {
                cerrarSesion()
            }
            R.id.op_mis_productos_v -> {
                replaceFragment(FragmentMisProductosV())
            }
            R.id.op_mis_ordenes_v -> {
                replaceFragment(FragmentOrdenesV())
            }
        }
        // Utiliza closeDrawer para cerrar el menú lateral
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
