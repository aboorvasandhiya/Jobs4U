package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Message> list;

    public RecyclerviewAdapter(Context context, ArrayList<Message> list)
    {
            this.context = context;
            this.list = list;
    }



    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.messege_iten,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder holder, int position) {

        holder.username.setText(list.get(position).getEmail());
        holder.date.setText(list.get(position).getdatetime());
        holder.message.setText(list.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, username, message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.Useremail);
            date = itemView.findViewById(R.id.user_messege_datetime);
            message = itemView.findViewById(R.id.user_messege);


        }
    }
}
