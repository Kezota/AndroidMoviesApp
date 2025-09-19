package com.example.moviesapp.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesapp.Adapters.CategoryEachFilmAdapter;
import com.example.moviesapp.Domains.Film;
import com.example.moviesapp.R;
import com.example.moviesapp.databinding.ActivityDetailBinding;

import eightbitlab.com.blurview.RenderScriptBlur;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void setVariable() {
        Film film = (Film) getIntent().getSerializableExtra("object");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new GranularRoundedCorners(0, 0, 50, 50));

        Glide.with(this)
                .load(film.getPoster())
                .apply(requestOptions)
                .into(binding.filmPic);

        binding.titleTxt.setText(film.getTitle());
        binding.imdbTxt.setText("IMDB " + film.getImdb());
        binding.movieTimesTxt.setText(film.getYear() + " - " + film.getTime());
        binding.movieSummery.setText(film.getDescription());

        binding.watchTrailerBtn.setOnClickListener(view -> {
            String id = film.getTrailer().replace("https://www.youtube.com/watch?v=", "");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(film.getTrailer()));
            try {
                startActivity(intent);
            } catch (Exception e) {
                startActivity(webIntent);
            }
        });

        binding.backImg.setOnClickListener(view -> finish());

        float radius = 10f; // corner radius, higher value = more rounded
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        binding.blurView.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);

        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurView.setClipToOutline(true);

        if (film.getCasts() != null) {
            binding.CastView.setAdapter(new com.example.moviesapp.Adapters.CastListAdapter(film.getCasts()));
            binding.CastView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }

        if (film.getGenre() != null) {
            binding.genreView.setAdapter(new CategoryEachFilmAdapter(film.getGenre()));
            binding.genreView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
    }
}