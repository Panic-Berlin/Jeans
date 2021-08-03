package com.example.priem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.priem.MainActivity
import com.example.priem.R
import com.example.priem.databinding.JeansItemBinding
import com.example.priem.model.Jeans
import com.google.android.material.snackbar.Snackbar

class JeansAdapter(
    private val jeans: List<Jeans>,
    private val onJeansClick: (jeans: Jeans) -> Unit
): RecyclerView.Adapter<JeansAdapter.CustomVIewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVIewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForJeans = layoutInflater.inflate(R.layout.jeans_item, parent, false)
        return CustomVIewHolder(cellForJeans)
    }

    override fun onBindViewHolder(holder: CustomVIewHolder, position: Int) {
        holder.bind(jeans[position])
        holder.itemView.setOnClickListener{
            onJeansClick.invoke(jeans[position])
        }

    }

    override fun getItemCount(): Int {
        return jeans.count()
    }

    class CustomVIewHolder(view: View): RecyclerView.ViewHolder(view){
        val viewBinding: JeansItemBinding by viewBinding(JeansItemBinding::bind)


        fun bind(jeans: Jeans){
            Glide.with(viewBinding.jeansImage).load(jeans.image).into(viewBinding.jeansImage)
            viewBinding.jeansDescription.text = jeans.title
            viewBinding.jeansOld.text = jeans.new.toString()
            viewBinding.jeansPrice.text = "${jeans.price.toString()}P"
            if(jeans.new == true){
                viewBinding.jeansOld.text = "NEW"
            }
            else{
                viewBinding.jeansOld.text = ""
            }

            var check: Boolean = false
            viewBinding.itemFavorite.setOnClickListener {
                if (!check){
                    Snackbar.make(itemView, "Товар добавлен в избранное", Snackbar.LENGTH_LONG).show()
                    viewBinding.itemFavorite.setImageResource(R.drawable.full_heart)
                    check = true
                }else{
                    viewBinding.itemFavorite.setImageResource(R.drawable.void_heart)
                    check = false
                }

            }


        }
    }
}


