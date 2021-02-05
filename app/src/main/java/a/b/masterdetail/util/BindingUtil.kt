package a.b.masterdetail.util

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["bind:imageUrl", "bind:errorDrawable"], requireAll = true)
fun loadImage(view: ImageView, url: String?, drawable: Drawable) {
    if (url == null) {
        Log.e("BindingAdapt", "loadImage: url is null somehow")
    } else {
        Glide.with(view.context).load(url).error(drawable).into(view)
    }
}