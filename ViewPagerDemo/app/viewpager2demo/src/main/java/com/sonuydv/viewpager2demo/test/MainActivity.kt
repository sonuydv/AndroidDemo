package com.example.navidrawerandrecyclerview

import android.os.Bundle
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonuydv.viewpager2demo.R

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var menuItemList: ArrayList<NaviMenuItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Menu items
        menuItemList = ArrayList()
        val item = NaviMenuItem()
        item.icon = R.drawable.ic_menu_camera
        item.title = "Camera"
        menuItemList.add(item)

        val item2 = NaviMenuItem()
        item2.icon = R.drawable.ic_menu_gallery
        item2.title = "Gallery"
        menuItemList.add(item2)

        val item3 = NaviMenuItem()
        item3.icon = R.drawable.ic_menu_slideshow
        item3.title = "Slide Show"
        menuItemList.add(item3)

        val item4 = NaviMenuItem()
        item4.icon = R.drawable.ic_menu_manage
        item4.title = "Setting"
        menuItemList.add(item4)

        val item5 = NaviMenuItem()
        item5.icon = R.drawable.ic_menu_share
        item5.title = "Share"
        menuItemList.add(item5)

        val item6 = NaviMenuItem()
        item6.icon = R.drawable.ic_menu_send
        item6.title = "Send"
        menuItemList.add(item6)

        val adapter = NaviDrawerAdapter(menuItemList)
        rv_navigation_menu.layoutManager = LinearLayoutManager(this)
        rv_navigation_menu.adapter = adapter

        mDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close) {
            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                Toast.makeText(this@MainActivity, "Closed", Toast.LENGTH_SHORT).show()
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                Toast.makeText(this@MainActivity, "Opened", Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        adapter.setOnItemClickLister(object: NaviDrawerAdapter.OnItemSelecteListener {
            override fun onItemSelected(v: View, position: Int) {
                drawer_layout.closeDrawer(GravityCompat.START)
                tv_content.text = menuItemList[position].title
                Snackbar.make(v, menuItemList[position].title + " clicked", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

}
