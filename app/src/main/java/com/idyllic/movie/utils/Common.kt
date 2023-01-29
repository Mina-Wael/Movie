package com.idyllic.movie.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.snackbar.Snackbar

object Common {

    //    fun getProgressLoading():CircularProgressDrawable{
//
//    }
    fun Fragment.showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

}