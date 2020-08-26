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

public class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.SelectCategoryHolder> {
    private List<Category> categories = new ArrayList<>();
    ArrayList<Category> checkedCategories = new ArrayList<>();

    public boolean isSelectedAll = false;

    @NonNull
    @Override
    public SelectCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_category_item, parent, false);
        return new SelectCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCategoryHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.textViewSelectCategory.setText(currentCategory.getCategory());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                CheckBox checkBox = (CheckBox) v;

                if (checkBox.isChecked()) {
                    checkedCategories.add(categories.get(position));
                    if (checkedCategories.size() == categories.size()) {
                        isSelectedAll = true;
                    }
                } else if (!checkBox.isChecked()) {
                    checkedCategories.remove(categories.get(position));
                    isSelectedAll = false;
                }
            }
        });

        if (!isSelectedAll) {
            holder.checkBoxSelect.setChecked(false);
            checkedCategories.clear();
        } else {
            holder.checkBoxSelect.setChecked(true);
            checkedCategories.clear();
            checkedCategories.addAll(categories);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    class SelectCategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewSelectCategory;
        private CheckBox checkBoxSelect;

        ItemClickListener itemClickListener;

        public SelectCategoryHolder(View itemView) {
            super(itemView);
            textViewSelectCategory = itemView.findViewById(R.id.text_view_select_category);
            checkBoxSelect = itemView.findViewById(R.id.check_box_select_category);

            checkBoxSelect.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }
    }

    public void selectAll() {
        isSelectedAll = true;
        notifyDataSetChanged();
    }

    public void clearAll() {
        isSelectedAll = false;
        notifyDataSetChanged();
    }
}