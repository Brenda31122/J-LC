package com.daniel.tienda.Cliente

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.daniel.tienda.Cliente.Nav_Fragments_Cliente.FragmentInicio_C
import com.daniel.tienda.R
import com.daniel.tienda.databinding.ActivityMainClienteBinding
import com.daniel.tienda.databinding.ActivityMainVendedorBinding
import com.google.android.material.navigation.NavigationView


class MainActivityCliente : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        binding.navigationView.setNavigationItemSelectedListener(this)

        val  toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(FragmentInicio_C())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment,fragment)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

    }
}