package com.mayank.amf_devops.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.amf_devops.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<String> sTitles;
    private Context context;

    public RecyclerViewAdapter(Context context,ArrayList<String> sTitles) {
        this.sTitles = sTitles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        layoutInflater.inflate(R.layout.list_item_view, parent, false);
        return new RecyclerViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String temp = sTitles.get(position);
        if (!temp.isEmpty())
        {
            holder.tvTitle.setText(temp);
        }

    }

    @Override
    public int getItemCount() {
        return sTitles.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleiv);
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
