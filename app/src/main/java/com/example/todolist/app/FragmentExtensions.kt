package com.example.todolist.app

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showProgress
import com.google.android.material.snackbar.Snackbar


fun Fragment.spannableSentence(
    start: Int,
    end: Int,
    text: Int,
    navigation: Int,
    textView: TextView
) {

    val spannable = SpannableString(getString(text))
    val fcsBlue = ForegroundColorSpan(Color.BLUE)
    spannable.setSpan(fcsBlue, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


    val clickable: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            findNavController().navigate(navigation)
        }
    }
    spannable.setSpan(clickable, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.movementMethod = LinkMovementMethod.getInstance()
    textView.text = spannable

}

fun Fragment.showProgressButton(textView: TextView) {

    bindProgressButton(textView)
    textView.attachTextChangeAnimator()

    textView.showProgress {
        buttonTextRes = R.string.loading
        progressColor = Color.WHITE

    }
}

fun Fragment.snackBar(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
}
