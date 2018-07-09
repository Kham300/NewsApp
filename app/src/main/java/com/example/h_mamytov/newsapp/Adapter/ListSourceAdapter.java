package com.example.h_mamytov.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.h_mamytov.newsapp.Common.Common;
import com.example.h_mamytov.newsapp.Interfaces.IconBetterIdeaService;
import com.example.h_mamytov.newsapp.Interfaces.ItemClickListener;
import com.example.h_mamytov.newsapp.R;
import com.example.h_mamytov.newsapp.model.IconBetterIdea;
import com.example.h_mamytov.newsapp.model.WebSite;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSourceAdapter  extends RecyclerView.Adapter<ListSourceAdapter.ListSourceViewHolder>{
    private Context context;
    private WebSite webSite;
    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;
        mService = Common.getIconService();
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.source_layout, viewGroup, false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder listSourceViewHolder, int i) {
        StringBuilder iconBetterApi = new StringBuilder("https//icons.better-idea.org/allicons.json?url=");
        iconBetterApi.append(webSite.getSources().get(i).getUrl());

        mService.getIconUrl(iconBetterApi.toString())
                .enqueue(new Callback<IconBetterIdea>() {
                    @Override
                    public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                        if (response.body().getIcons().size()>0){
                            Picasso.with(context)
                                    .load(response.body().getIcons().get(0).getUrl())
                                    .into(listSourceViewHolder.source_image);


                        }
                    }

                    @Override
                    public void onFailure(Call<IconBetterIdea> call, Throwable t) {

                    }
                });

        listSourceViewHolder.source_title.setText(webSite.getSources().get(i).getName());

        listSourceViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //implement later

            }
        });
    }


    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }

    class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener itemClickListener;
        TextView source_title;
        CircleImageView source_image;

        public ListSourceViewHolder(@NonNull View itemView) {
            super(itemView);
            source_image = itemView.findViewById(R.id.source_image);
            source_title = itemView.findViewById(R.id.source_name);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
