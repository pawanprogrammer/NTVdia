package com.example.railwayhvac

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_item.view.*
import java.util.ArrayList

class Adapter(val context: Context,val menuList: ArrayList<Menu>):RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view=LayoutInflater.from(p0.context).inflate(R.layout.menu_item,p0,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.bindview(menuList[p1])

        p0.itemView.setOnClickListener {

            when(p1){

                0->{

                    (context as MainActivity).drawer_layout.closeDrawer(Gravity.START)
                }

                1->{

                    context.startActivity(Intent(context,Profile::class.java))
                }

                2->{
                    context.startActivity(Intent(context,ContactUs::class.java))

                }

                3->{

//                    val intent=Intent(Intent.ACTION_SEND)
//                    intent.putExtra(Intent.EXTRA_TEXT,"")
//                    context.startActivity(Intent.createChooser(intent,"share via"))
                }
            }



        }
    }


    class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){


        fun bindview(menu: Menu){

            itemView.menu_image.setImageResource(menu.icon!!)
            itemView.menu_text.text=menu.text

        }
    }

}