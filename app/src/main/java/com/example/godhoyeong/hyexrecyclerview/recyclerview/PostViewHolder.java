package com.example.godhoyeong.hyexrecyclerview.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.godhoyeong.hyexrecyclerview.R;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView ivImage,ivShare;
    public TextView tvLikeCount,tvUserName,tvPostText;
    public CheckBox cbLike;
    private PostAdapter mAdapter;


    public PostViewHolder(View itemView, PostAdapter postAdapter) {
        super(itemView);

        mAdapter = postAdapter;

        ivImage = (ImageView)itemView.findViewById(R.id.iv_Image);
//        ivLike = (ImageView)itemView.findViewById(R.id.iv_Like);

        ivShare = (ImageView)itemView.findViewById(R.id.iv_Share);
        tvLikeCount = (TextView)itemView.findViewById(R.id.tv_LikeCount);
        tvUserName = (TextView)itemView.findViewById(R.id.tv_UserName);
        tvPostText = (TextView)itemView.findViewById(R.id.tv_PostText);
        cbLike = (CheckBox)itemView.findViewById(R.id.cb_Like);
        cbLike.setOnClickListener(this);
        ivShare.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        int position = getAdapterPosition();

        switch (view.getId()){
            case R.id.cb_Like :
//                mAdapter.onLikeClicked(position);


                break;
            case R.id.iv_Share:
                break;
        }


    }

}
