package au.com.carsales.basemodule.util.databinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;

import au.com.carsales.basemodule.BaseModuleApplication;
import au.com.carsales.basemodule.R;

public class DataBindingUtilAdapters {

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, float width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) width;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, float height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) height;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(NetworkImageView imageView, String url) {
        ImageLoader imageLoader = BaseModuleApplication.Companion.getAppImageLoader();
        imageView.setImageUrl(url, imageLoader);
    }

    @BindingAdapter("loadImageUrl")
    public static void loadImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_base_no_image)
                .error(R.drawable.ic_base_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @BindingAdapter("loadIconMaterialButtonUrl")
    public static void loadIconMaterialButtonUrl(MaterialButton materialButton, String url) {
        Glide.with(materialButton.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        materialButton.setIcon(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        materialButton.setVisibility(View.INVISIBLE);
                    }
                });
    }
}