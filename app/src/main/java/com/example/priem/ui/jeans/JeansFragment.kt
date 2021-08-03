package com.example.priem.ui.jeans

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.priem.MainActivity
import com.example.priem.R
import com.example.priem.adapter.JeansAdapter
import com.example.priem.databinding.FragmentJeansBinding
import com.example.priem.model.Jeans
import com.example.priem.ui.description.JeansDescriptionFragment
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [JeansFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("UNREACHABLE_CODE")
class JeansFragment : Fragment(R.layout.fragment_jeans) {

    lateinit var  viewBinding: FragmentJeansBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewRoot = view.findViewById<View>(R.id.root_jeans)

        viewBinding = FragmentJeansBinding.bind(viewRoot)
        viewBinding.mainRV.layoutManager = GridLayoutManager(MainActivity(), 2)

        json()


    }


    fun json() {
        val url = "https://static.upstarts.work/tests/jeans/jeans-default.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("App Error", e.toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val jeansType = object : TypeToken<List<Jeans>>() {}.type
                val jeans = gson.fromJson<List<Jeans>>(body, jeansType)



                activity?.runOnUiThread(){
                    viewBinding.jeansCount.text = "Найдено: ${jeans.count()} товаров"
                    viewBinding.mainRV.layoutManager = GridLayoutManager(activity, 2)
                    viewBinding.mainRV.adapter = JeansAdapter(jeans){
                        findNavController().navigate(R.id.action_jeansFragment_to_jeansDescriptionFragment, bundleOf(
                            "jeans" to it
                        ))
                    }
                }




            }
        })

    }
}