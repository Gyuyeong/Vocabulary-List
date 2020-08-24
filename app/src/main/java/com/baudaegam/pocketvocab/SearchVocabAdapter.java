package com.baudaegam.pocketvocab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchVocabAdapter extends RecyclerView.Adapter<SearchVocabAdapter.SearchVocabHolder> implements Filterable {
    private List<Vocab> vocabs = new ArrayList<>();
    private List<Vocab> vocabsFull;


    @NonNull
    @Override
    public SearchVocabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_vocab_item, parent, false);
        return new SearchVocabHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVocabHolder holder, int position) {
        Vocab currentVocab = vocabs.get(position);
        vocabsFull = new ArrayList<>(vocabs);
        holder.textViewSearchVocab.setText(currentVocab.getVocab());
        holder.textViewSearchMeaning.setText(currentVocab.getMeaning());
        holder.textViewSearchCategory.setText(String.format("%d", currentVocab.getCategoryId()));
    }

    @Override
    public int getItemCount() {
        return vocabs.size();
    }

    public void setSearchVocabs(List<Vocab> vocabs) {
        this.vocabs = vocabs;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return vocabFilter;
    }

    private Filter vocabFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Vocab> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(vocabsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Vocab vocab : vocabsFull) {
                    if (vocab.getVocab().toLowerCase().contains(filterPattern)) {
                        filteredList.add(vocab);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            vocabs.clear();
            vocabs.addAll((List<Vocab>) results.values);
            notifyDataSetChanged();
        }
    };

    class SearchVocabHolder extends RecyclerView.ViewHolder {
        private TextView textViewSearchVocab;
        private TextView textViewSearchMeaning;
        private TextView textViewSearchCategory;

        public SearchVocabHolder(View itemView) {
            super(itemView);
            textViewSearchVocab = itemView.findViewById(R.id.text_view_search_vocab);
            textViewSearchMeaning = itemView.findViewById(R.id.text_view_search_meaning);
            textViewSearchCategory = itemView.findViewById(R.id.text_view_search_category);
        }
    }
}
