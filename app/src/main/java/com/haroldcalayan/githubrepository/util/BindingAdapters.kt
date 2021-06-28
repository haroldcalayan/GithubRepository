package com.haroldcalayan.githubrepository.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.haroldcalayan.githubrepository.R


@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).placeholder(R.mipmap.ic_github).error(R.mipmap.ic_github).into(view)
}