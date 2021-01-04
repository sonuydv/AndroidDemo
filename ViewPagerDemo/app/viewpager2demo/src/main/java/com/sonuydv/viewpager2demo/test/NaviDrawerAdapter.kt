package com.example.navidrawerandrecyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.nav_menu_item.view.*


class NaviDrawerAdapter(private val drawerMenuList: List<NaviMenuItem>) : RecyclerView.Adapter<NaviDrawerAdapter.DrawerViewHolder>() {
    private var mListener: OnItemSelecteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerViewHolder {
        val view: View
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.nav_header_main, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.nav_menu_item, parent, false)
        }

        return DrawerViewHolder(view, viewType)
    }


    override fun onBindViewHolder(holder: DrawerViewHolder, position: Int) {
        if (position == 0) {
            holder.headerProfleImage.setImageResource(android.R.drawable.sym_def_app_icon)
            holder.headerTitle.text = "Android Developer"
            holder.headerEmail.text = "yours@example.com"
        } else {
            holder.title.text = drawerMenuList[position - 1].title
            holder.icon.setImageResource(drawerMenuList[position - 1].icon)
        }
    }

    override fun getItemCount(): Int {
        return drawerMenuList.size + 1
    }


    override fun getItemViewType(position: Int): Int {

        return if (position == 0) {
            TYPE_HEADER
        } else TYPE_MENU

    }

    inner class DrawerViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        lateinit var headerProfleImage: ImageView
        lateinit var headerTitle: TextView
        lateinit var headerEmail: TextView

        lateinit var title: TextView
        lateinit var icon: ImageView

        init {
            if (viewType == 0) {
                headerProfleImage = itemView.iv_profile_pic
                headerTitle = itemView.tv_title
                headerEmail = itemView.tv_email
            } else {
                title = itemView.title
                icon = itemView.icon
            }
            itemView.setOnClickListener { view ->
                val pos = adapterPosition
                if (pos > 0) mListener!!.onItemSelected(view, pos - 1)
            }
        }
    }


    fun setOnItemClickLister(mListener: OnItemSelecteListener) {
        this.mListener = mListener
    }

    interface OnItemSelecteListener {
        fun onItemSelected(v: View, position: Int)
    }

    companion object {
        val TYPE_HEADER = 0
        val TYPE_MENU = 1
    }

}