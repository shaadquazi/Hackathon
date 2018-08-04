package com.example.shaad.snpppapp.Utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shaad.snpppapp.R;

import java.util.List;

/**
 * Created by Shaad on 21-03-2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<FAQ> mList;
    private Context context;

    public RecyclerAdapter(List<FAQ> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_faq_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FAQ faq = mList.get(position);
        holder.mQuestion.setText(faq.get_question());
        holder.mAnswer.setText(faq.get_answer());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mQuestion;
        private TextView mAnswer;
        public MyViewHolder(View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.faq_question);
            mAnswer = itemView.findViewById(R.id.faq_answer);
        }
    }
}
