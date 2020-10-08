package com.example.notes.util

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.example.notes.R

object NavHelper {

    fun navigateWithStack(view: View, actionID: Int ,bundle : Bundle?){
        findNavController(view).navigate(actionID, bundle)
    }

    fun navigateWithoutStack(view: View, actionID: Int ,bundle : Bundle?){
        val navigation = NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
        findNavController(view).navigate(actionID, bundle, navigation)
    }

}