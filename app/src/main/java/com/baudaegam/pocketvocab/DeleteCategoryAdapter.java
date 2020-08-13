package com.baudaegam.pocketvocab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DeleteCategoryAdapter extends RecyclerView.Adapter<DeleteCategoryAdapter.DeleteCategoryHolder> {
    private List<Category> categories = new ArrayList<>();
    ArrayList<Category> checkedCategories = new ArrayList<>();

    @NonNull
    @Override
    public DeleteCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delete_category_item, parent, false);
        return new DeleteCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteCategoryHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.textViewDeleteCategory.setText(currentCategory.getCategory());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                CheckBox checkBox = (CheckBox) v;

                if (checkBox.isChecked()) {
                    checkedCategories.add(categories.get(position));
                } else if (!checkBox.isChecked()) {
                    checkedCategories.remove(categories.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    class DeleteCategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewDeleteCategory;
        private CheckBox checkBoxDelete;

        ItemClickListener itemClickListener;

        public DeleteCategoryHolder(View itemView) {
            super(itemView);
            textViewDeleteCategory = itemView.findViewById(R.id.text_view_delete_category);
            checkBoxDelete = itemView.findViewById(R.id.check_box_delete_category);

            checkBoxDelete.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}
