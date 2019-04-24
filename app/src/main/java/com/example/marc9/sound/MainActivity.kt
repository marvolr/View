package com.example.marc9.sound


import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.*
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    init {
        instance = this
    }
    private lateinit var drawer: DrawerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        supportActionBar?.setDisplayShowTitleEnabled(true)
        setSupportActionBar(toolbar)


        logo.setOnClickListener {
            setFragment(TabFragment())

        }



//Navigation Drawer Action Bar Toggle Action Bar
        drawer = findViewById(R.id.drawer_layout)
        val mDrawerToggle =ActionBarDrawerToggle(this,drawer,toolbar, R.string.app_name,R.string.app_name)
        drawer.setDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()


        //FRAGMENTS

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        setFragment(TabFragment())//init




       /* val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener{
            menuItem -> drawer.closeDrawers()
            if ( menuItem.itemId==R.id.profile){
                val  ft=mFragmentManager.beginTransaction()
                ft.replace(R.id.container,TabFragment()).commit()

            }
            if ( menuItem.itemId==R.id.friends){
                val  ft=mFragmentManager.beginTransaction()
                ft.replace(R.id.container,PerfilFragment()).commit()

            }
            if ( menuItem.itemId==R.id.chat){
                val  ft=mFragmentManager.beginTransaction()
                ft.replace(R.id.container,PerfilFragment()).commit()

            }
            if ( menuItem.itemId==R.id.perfil){
                val  ft=mFragmentManager.beginTransaction()
                ft.replace(R.id.container,PerfilFragment()).commit()

            }
            if ( menuItem.itemId==R.id.center_control){
                val  ft=mFragmentManager.beginTransaction()
                ft.replace(R.id.container,PerfilFragment()).commit()

            }
            if ( menuItem.itemId==R.id.reactions){
                val  ft=mFragmentManager.beginTransaction()
                ft.replace(R.id.container,PerfilFragment()).commit()

            }
            if ( menuItem.itemId==R.id.actions){
                val  ft=mFragmentManager.beginTransaction()
                ft.replace(R.id.container,PerfilFragment()).commit()
            }

         false
        }*/

    }

//Location




    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun setFragment(fragment: Fragment?) {
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_main, fragment)
            ft.commit()
        }
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        if ( menuItem.itemId==R.id.profile){
           setFragment(PerfilFragment())

        }
        if ( menuItem.itemId==R.id.friends){
            setFragment(PerfilFragment())

        }
        if ( menuItem.itemId==R.id.chat){

            setFragment(PerfilFragment())

        }
        if ( menuItem.itemId==R.id.perfil){
            setFragment(PerfilFragment())


        }
        if ( menuItem.itemId==R.id.center_control){
            setFragment(PerfilFragment())


        }
        if ( menuItem.itemId==R.id.reactions){
            setFragment(PerfilFragment())


        }
        if ( menuItem.itemId==R.id.actions){
            setFragment(PerfilFragment())


        }
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.perfil -> true
            R.id.contacto->true
            R.id.feed_back->true
            R.id.cerrar_sesion->true
            else -> super.onOptionsItemSelected(item)
        }
    }



    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }



}
