package com.example.administrator.sqlitetest.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sqlitetest.R;
import com.example.administrator.sqlitetest.server.Student;

import java.util.List;

/**
 * Created by Administrator on 2017/3/4.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Student> stuList;
    private MyItemClickListener mItemClickListener;
    private onDeleteLisener mListener;

    public MyAdapter(List<Student> stuList,onDeleteLisener mListener){
        this.stuList=stuList;
        this.mListener=mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.suudent_item,parent,false);
        ViewHolder holder=new ViewHolder(view,mItemClickListener);
        return holder;
    }

    public void setOnItemClickListener(MyItemClickListener mListener){
        this.mItemClickListener=mListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Student stu=stuList.get(position);
        holder.mImageView.setImageBitmap(stu.getBitmap());
        holder.tvName.setText(stu.getName());
        holder.tvId.setText(stu.getId());
        holder.tvAge.setText(stu.getAge()+" ");
        holder.tvSex.setText(stu.getSex());

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.del(Integer.valueOf(stu.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return stuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView,ivDel;
        private TextView tvName,tvId,tvAge,tvSex;
        private MyItemClickListener mListener;

        public ViewHolder(View itemView,MyItemClickListener mListener) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.ivImg);
            tvName= (TextView) itemView.findViewById(R.id.tvName);
            tvId= (TextView) itemView.findViewById(R.id.tvId);
            tvAge= (TextView) itemView.findViewById(R.id.tvAge);
            tvSex= (TextView) itemView.findViewById(R.id.tvSex);
            ivDel= (ImageView) itemView.findViewById(R.id.iv_delete);
            this.mListener=mListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }

        }
    }
}
