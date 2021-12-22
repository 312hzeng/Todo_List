package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import java.util.List;

//display data from the model into the recyclerview
public class ItemsAdaptor extends RecyclerView.Adapter<ItemsAdaptor.ViewHolder> {

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    //constructor
    public ItemsAdaptor(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //use layout inflater to inflate a view
        View TodoView = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        //wrap it inside a view holder and return it
        return new ViewHolder(TodoView);
    }

    //binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    //grab the item at the position
    String item = items.get(i);
    //bind the item into particular view holder
    viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //class to provide access visually to each row of the todo list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update the view in the view holder with the data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //notify adaptor which position is long clicked
                    longClickListener.onItemLongClicked(getAdapterPosition());

                    return true;
                }
            });
        }
    }
}
