package com.example.godhoyeong.hyexrecyclerview.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.godhoyeong.hyexrecyclerview.MyContract;
import com.example.godhoyeong.hyexrecyclerview.R;
import com.example.godhoyeong.hyexrecyclerview.model.PostItem;

import java.util.ArrayList;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>{

    private Context mContext;
    private Cursor mCursor;
    int count = 0;

    public PostAdapter(Context context, Cursor cursor, Activity parentActivity) {
        this.mContext = context;
        mCursor = cursor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.post_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;
        // 열의 이름으로 열의 번호를 넘겨줌
        String name = mCursor.getString(mCursor.getColumnIndex(MyContract.MyEntry.COLUMN_USER_NAME));
        String postText = mCursor.getString(mCursor.getColumnIndex(MyContract.MyEntry.COLUMN_TEXT));

        long id = mCursor.getLong(mCursor.getColumnIndex(MyContract.MyEntry._ID));



        holder.userNameView.setText(name);
        holder.postTextView.setText(postText);
        holder.itemView.setTag(id);

        holder.cbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cbLike.isChecked()){
                    count++;
                    holder.userLike.setText((count+""));
                }else if(!holder.cbLike.isChecked()){
                    count--;
                    holder.userLike.setText(count+"");
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }



    // 리사이클러뷰 내에서 단일의 아이템을 표시하는데 필요한 뷰를 보관 및 유지하는 내부 클래스
    class MyViewHolder extends RecyclerView.ViewHolder {

       final TextView userNameView;
        final TextView postTextView;
        CheckBox cbLike;
        final TextView userLike;

        // TextView 레퍼런스 가져옴
        public MyViewHolder(View itemView) {
            super(itemView);
            postTextView = itemView.findViewById(R.id.tv_PostText);
            userNameView = itemView.findViewById(R.id.tv_UserName);
            cbLike = itemView.findViewById(R.id.cb_Like);
            userLike = itemView.findViewById(R.id.tv_LikeCount);

        }

    }
    public void swapCursor(Cursor newCursor) {
        // 항상 이전 커서를 닫는다.
        if (mCursor != null)
            mCursor.close();
        // 새 커서로 업데이트
        mCursor = newCursor;
        // 리사이클러뷰 업데이트
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }
}

