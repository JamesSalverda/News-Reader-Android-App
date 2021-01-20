package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.parameter.Articles;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

// Adapter class for the recycler view
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHodler> {
    Context context;
    List<Articles> articles;
    public Adapter(Context context, List<Articles> articles){
        this.context = context;
        this.articles = articles;
    }

    // create card holder for recycler view
    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new ViewHodler(view);
    }

    // bind api items to the card holder
    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        final Articles art = articles.get(position);
        String url = art.getUrl();
        holder.newsTitle.setText(art.getTitle());
        holder.newsDate.setText(art.getPublishedAt());
        String imageUrl = art.getUrlToImage();
        Picasso.get().load(imageUrl).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsInDetails.class);
                intent.putExtra("url", art.getUrl());
                context.startActivity(intent);
            }
        });
    }

    // gets the item count from the article api
    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        TextView newsTitle, newsDate;
        ImageView imageView;
        CardView cardView;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);

            newsDate = itemView.findViewById(R.id.newsDate);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
