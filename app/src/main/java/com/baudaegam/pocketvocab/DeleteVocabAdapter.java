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

public class DeleteVocabAdapter extends RecyclerView.Adapter<DeleteVocabAdapter.DeleteVocabHolder> {
    private List<Vocab> vocabs = new ArrayList<>();
    ArrayList<Vocab> checkedVocabs = new ArrayList<>();

    @NonNull
    @Override
    public DeleteVocabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delete_vocab_item, parent, false);
        return new DeleteVocabHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteVocabHolder holder, int position) {
        Vocab currentVocab = vocabs.get(position);
        holder.textViewDeleteVocab.setText(currentVocab.getVocab());
        holder.textViewDeleteMeaning.setText(currentVocab.getMeaning());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                CheckBox checkBox = (CheckBox) v;

                // check if it is checked or not
                if (checkBox.isChecked()) {
                    checkedVocabs.add(vocabs.get(position));
                } else if (!checkBox.isChecked()){
                    checkedVocabs.remove(vocabs.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vocabs.size();
    }

    public void setVocabs(List<Vocab> vocabs) {
        this.vocabs = vocabs;
        notifyDataSetChanged();
    }

    class DeleteVocabHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewDeleteVocab;
        private TextView textViewDeleteMeaning;
        private CheckBox checkBoxDelete;

        ItemClickListener itemClickListener;

        public DeleteVocabHolder(View itemView) {
            super(itemView);
            textViewDeleteVocab = itemView.findViewById(R.id.text_view_delete_vocab);
            textViewDeleteMeaning = itemView.findViewById(R.id.text_view_delete_meaning);
            checkBoxDelete = itemView.findViewById(R.id.check_box_delete_vocab);

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
