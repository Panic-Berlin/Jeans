package com.example.priem.ui.description

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.priem.R
import com.example.priem.databinding.FragmentJeansDescriptionBinding
import com.example.priem.model.Jeans
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class JeansDescriptionFragment: Fragment(R.layout.fragment_jeans_description) {

    lateinit var viewBinding: FragmentJeansDescriptionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentJeansDescriptionBinding.bind(view)

        viewBinding.navToolBar.setOnClickListener {
            findNavController().navigate(R.id.action_jeansDescriptionFragment_to_jeansFragment)
        }

        var check: Boolean = false
        viewBinding.addFavorites.setOnClickListener {
            if (!check){
                Snackbar.make(view, "Товар добавлен в избранное", Snackbar.LENGTH_LONG).show()
                viewBinding.addFavorites.setImageResource(R.drawable.full_heart)
                check = true
            }else{
                viewBinding.addFavorites.setImageResource(R.drawable.void_heart)
                check = false
            }
        }

        (arguments?.getSerializable("jeans") as? Jeans)?.let { jeans ->
            viewBinding.jeansDescriptionDes.text = jeans.title
            Glide.with(requireContext()).load(jeans.image).into(viewBinding.jeansImageDescription)
            viewBinding.jeansPriceDescription.text = "${jeans.price}P"


        }
    }
}