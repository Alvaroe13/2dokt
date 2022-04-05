package com.example.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment(R.layout.fragment_splash) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateWithoutStack()
    }

    private fun navigateWithoutStack(){
        val nav = NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
        findNavController().navigate(R.id.action_splashFragment_to_list_navigation, null, nav)
    }
}