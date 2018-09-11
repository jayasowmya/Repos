package com.example.jayasaripalli.repos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jayasaripalli.repos.GlideApp;
import com.example.jayasaripalli.repos.R;
import com.example.jayasaripalli.repos.model.Repository;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewholder> {
    private List<Repository> repositories;
    private int rowLayout;
    private Context context;
    public RepoAdapter(List<Repository> repositories, int rowLayout, Context context) {
        this.repositories = repositories;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class RepoViewholder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView profileImgView;
        TextView repoNum;
        public RepoViewholder(View v) {
            super(v);
            name = v.findViewById(R.id.name_title);
            /*profileImgView=v.findViewById(R.id.profile_img);
            repoNum=v.findViewById(R.id.numberRepos);*/
        }
    }
    @Override
    public RepoAdapter.RepoViewholder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RepoViewholder(view);
    }
    @Override
    public void onBindViewHolder(RepoViewholder holder, final int position) {

        holder.name.setText(repositories.get(position).getName());
      /* // holder.repoNum.setText(getItemCount());
        GlideApp.with(context)
                .load(repositories.get(position).getOwner().getAvatarUrl()+".png")
                .override(300, 200)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(holder.profileImgView);*/
       // holder.repoNum.setText(repositories.size());

    }
    @Override
    public int getItemCount() {
        return repositories.size();
    }
}
