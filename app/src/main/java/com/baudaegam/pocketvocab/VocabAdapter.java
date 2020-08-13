package com.baudaegam.pocketvocab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.VocabHolder> {
    private List<Vocab> vocabs = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Vocab vocab);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public VocabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocab_item, parent, false);
        return new VocabHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabHolder holder, int position) {
        Vocab currentVocab = vocabs.get(position);
        holder.textViewVocab.setText(currentVocab.getVocab());
        holder.textViewMeaning.setText(currentVocab.getMeaning());
        holder.textViewCount.setText(String.valueOf(currentVocab.getCount()));
    }

    @Override
    public int getItemCount() {
        return vocabs.size();
    }

    public void setVocabs(List<Vocab> vocabs) {
        this.vocabs = vocabs;
        notifyDataSetChanged();
    }

    class VocabHolder extends RecyclerView.ViewHolder {
        private TextView textViewVocab;
        private TextView textViewMeaning;
        private TextView textViewCount;

        public VocabHolder(View itemView) {
            super(itemView);
            textViewVocab = itemView.findViewById(R.id.text_view_vocab);
            textViewMeaning = itemView.findViewById(R.id.text_view_meaning);
            textViewCount = itemView.findViewById(R.id.text_view_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(vocabs.get(position));
                    }
                }
            });
        }
    }
}
