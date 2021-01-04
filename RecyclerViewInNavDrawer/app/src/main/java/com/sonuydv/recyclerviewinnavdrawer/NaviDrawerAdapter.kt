package com.example.navidrawerandrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sonuydv.recyclerviewinnavdrawer.R
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.nav_menu_item.view.*


class NaviDrawerAdapter(private val drawerMenuList: List<NaviMenuItem>,private val mListener:OnItemSelecteListener) : RecyclerView.Adapter<NaviDrawerAdapter.DrawerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nav_menu_item, parent, false)
        return DrawerViewHolder(view, viewType)
    }


    override fun onBindViewHolder(holder: DrawerViewHolder, position: Int) {
//        if (position == 0) {
//            holder.headerProfleImage.setImageResource(android.R.drawable.sym_def_app_icon)
//            holder.headerTitle.text = "Android Developer"
//            holder.headerEmail.text = "yours@example.com"
//        } else {
//            holder.title.text = drawerMenuList[position - 1].title
//            holder.icon.setImageResource(drawerMenuList[position - 1].icon)
//        }

        holder.title.text=drawerMenuList[position].title
    }

    override fun getItemCount(): Int {
        return drawerMenuList.size;
    }


    inner class DrawerViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        lateinit var title: TextView
        init {
            title = itemView.textView
            itemView.setOnClickListener { view ->
                val pos = adapterPosition
                if (pos > 0) mListener!!.onItemSelected(view, pos - 1)
            }
        }
    }
    interface OnItemSelecteListener {
        fun onItemSelected(v: View, position: Int)
    }

}