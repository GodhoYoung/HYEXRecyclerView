package com.example.godhoyeong.hyexrecyclerview.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.godhoyeong.hyexrecyclerview.R;
import com.example.godhoyeong.hyexrecyclerview.data.DataPostItem;

import java.util.ArrayList;



public class TimelineFragment extends Fragment {

    ArrayList<DataPostItem> arrayList;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // MakeData
        arrayList = new ArrayList<>();
        DataPostItem item = new DataPostItem(0,
                "http://cphoto.asiae.co.kr/listimglink/6/2018012517534173354_1516870418.jpg",
                "불꽃놀이했어요~", "ansta_", 1234, false);
        arrayList.add(item);
        arrayList.add(new DataPostItem(1, "http://newsimg.sedaily.com/2017/09/15/1OL1CMWR9X_1.jpg",
                "하이!", "g82", 200000, false));

        arrayList.add(new DataPostItem(2, "http://newsimg.sedaily.com/2017/09/15/1OL1BI7GVZ_1.jpg",
                "하ggggg이!", "g82", 200000, false));

        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.fragment_timeline, container, false);
        RecyclerView recyclerView = (RecyclerView) baseView.findViewById(R.id.rv_List_frg);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new PostViewAdapter());

        baseView.findViewById(R.id.fab_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(cameraIntent,1000);
                }
            }
        });

        return baseView;
    }


    /**
     * FragActivity(TimelineFragment)  -->  ext run camera --> FragActivity --> cameraActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK){
            Log.d("onActivityResult","camera success");

            Intent startIntent = new Intent(getActivity(), CameraActivity.class);
            startIntent.setData(data.getData());

            startActivity(startIntent);

//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap)extras.get("data");
//            ImageView imageView;
//            imageView.setImageBitmap(imageBitmap);
        }
    }

    class PostViewAdapter extends RecyclerView.Adapter<PostViewHolder> {

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View baseView = getActivity().getLayoutInflater().inflate(R.layout.item_post_frag, parent, false);
            PostViewHolder postViewHolder = new PostViewHolder(baseView);
            return postViewHolder;
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {

            DataPostItem item = arrayList.get(position);

            String url = item.getPostImgUrl();

            Glide.with(TimelineFragment.this)
                    .load(url)
                    .centerCrop()
                    .crossFade()
                    .into(holder.iv_post);

            holder.tv_username.setText(item.getUserName());
            holder.tv_posttext.setText(item.getPostText());
            holder.tv_postlikecount.setText( String.valueOf( item.getPost_likes_count() ) );
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_username, tv_postlikecount, tv_posttext;
        public ImageView iv_post;

        public PostViewHolder(View itemView) {
            super(itemView);
            iv_post = (ImageView) itemView.findViewById(R.id.iv_post_img);
            tv_username = (TextView) itemView.findViewById(R.id.tv_user_nickname);
            tv_postlikecount = (TextView) itemView.findViewById(R.id.tv_like_count);
            tv_posttext = (TextView) itemView.findViewById(R.id.tv_post_text);

        }
    }

}
