package com.example.newsapi;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.sip.SipSession;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import retrofit2.Callback;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.MyViewHolder> {
    private static final String TAG ="ghost" ;
     public List<Article> articleList;
    Context context;
    OnItemClickListener mlistener;
   /* public void setOnItemClickListener(Callback<Example> listener)
    {
        mlistener= (OnItemClickListener) listener;
    }*/

    public ExampleAdapter(  Context context,List<Article> articleList)
    {

        this.context=context;
        this.articleList=articleList;
        this.mlistener= (OnItemClickListener) context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.item,parent,false);

        return new MyViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.bottom_shadow);
        requestOptions.error(R.drawable.bottom_shadow);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();


        String str=String.valueOf(position);
        Log.d("ghost",str);
        Glide.with(context).load((articleList.get(position)).getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);
        holder.title.setText((articleList.get(position)).getTitle());
        holder.desc.setText((articleList.get(position)).getDescription());
        holder.source.setText((articleList.get(position)).getSource().getName());
        holder.time.setText(" \u2022 "+Utils.DateToTimeFormat((articleList.get(position)).getPublishedAt()));
        holder.published_at.setText(Utils.DateFormat((articleList.get(position)).getPublishedAt()));
        holder.author.setText((articleList.get(position)).getAuthor());


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView title,desc,author,published_at,source,time;
        ImageView imageView;
        ProgressBar progressBar;
        FrameLayout frameLayout;

        public MyViewHolder(@NonNull View itemView,OnItemClickListener mlistener) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            author=itemView.findViewById(R.id.author);
            published_at=itemView.findViewById(R.id.publishedAt);
            source=itemView.findViewById(R.id.source);
            time=itemView.findViewById(R.id.time);
            imageView=itemView.findViewById(R.id.img);
            frameLayout=itemView.findViewById(R.id.frame_layout);
            progressBar=itemView.findViewById(R.id.progress_load_photo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.onItemClick(v,getAdapterPosition());
                }
            });
        }

    }
    public interface OnItemClickListener
    {
        void onItemClick(View view,int position);
    }
}
//Failed to find GeneratedAppGlideModule. You should include an annotationProcessor
// compile dependency on com.github.bumptech.glide:compiler in your
// application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored
