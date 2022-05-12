package com.dti.test.gitusers.common.util

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dti.test.gitusers.R


@BindingAdapter("imageUrl", requireAll = false)
fun loadImage(view: View, url:String?){

    url?.let {
        println("image url is $it")
        Glide.with(view.context)
            .load(it)
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .into(view as ImageView)
    }

}

@BindingAdapter("isFavourite", requireAll = false)
fun isFavourite(view:ImageButton,value:Boolean?){
    println("value is $value")
    value?.let {
        if(it){
            view.setImageResource(R.drawable.ic_favorite_on)
        }
        else{
            view.setImageResource(R.drawable.ic_favourite_off)
        }
    }
}
