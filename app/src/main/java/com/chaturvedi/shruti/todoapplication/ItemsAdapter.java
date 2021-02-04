package com.chaturvedi.shruti.todoapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    //public interface that the main activity will implement, and we will call from the viewholder.
    public interface OnLongClickListener {
        void onItemLongCLicked(int position);
    }

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    public interface onCheckBox {
        void onItemChecked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;
    onCheckBox checkBox;


    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener, onCheckBox checkBox) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
        this.checkBox = checkBox;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Returns a view-holder for items in the list and wraps the view inside the viewholder
        //View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        return new ViewHolder(todoView); //wrapping of the view inside the viewholder
    }

    //Bind data to a particular view holder. The view holder we use is the one returned by onCreateViewHolder (simple_list_item_1)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Container to provide easy access to view that represent each row
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;
        CheckBox checkBoxItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //tvItem = itemView.findViewById(android.R.id.text1);
            tvItem = itemView.findViewById(R.id.text1);
            checkBoxItem = itemView.findViewById(R.id.checkBox);
        }

        //We do not want the Adapter to handle the data, instead we pass it to the bind method
        //of the view holder to handle the data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Notify the listener which item was long-pressed
                    longClickListener.onItemLongCLicked(getAdapterPosition());
                    return true;
                }
            });

            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });

            checkBoxItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.onItemChecked(getAdapterPosition());
                }
            });
        }
    }
}
