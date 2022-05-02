package com.mental_health.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mental_health.Exercises;
import com.mental_health.R;
import com.mental_health.dataModel.DataModelExercises;

import java.util.ArrayList;

public class ExercisesAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<DataModelExercises> items;
    private Exercises mActivity;

    public ExercisesAdapter(ArrayList<DataModelExercises> data, Exercises activity) {
        this.items = data;
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(mActivity);
    }

    public void addItem(DataModelExercises result) {
        items.add(result);
    }

    public void setInflater(LayoutInflater layoutInflater){
        this.mInflater =layoutInflater;
    }

    public void replaceItems(ArrayList<DataModelExercises> newItems) {
        this.items.clear();
        for(DataModelExercises item: newItems)
            this.items.add(item);
    }

    public void insertItem(DataModelExercises item) {
        items.add(0, item);
    }

    public void clearItems(){
        items.clear();
    }

    public void AddResults(ArrayList<DataModelExercises> result) {
        items.addAll(result);
    }

    public DataModelExercises getItemsAt(int position){
        return  items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataModelExercises model = items.get(position);
        ExerciseViewHolder ExerciseViewHolder = (ExerciseViewHolder) holder;
//        ExerciseViewHolder.imageViewIcon.setBackgroundResource(model.getImage());
        ExerciseViewHolder.textViewName.setText(model.getName());
        ExerciseViewHolder.textViewStatus.setText(model.getDetails());
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootCategoryView = mInflater.inflate(R.layout.row_item_exercise, parent, false);
        return new ExerciseViewHolder(rootCategoryView, this);
    }

    private class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageViewIcon;
        private TextView textViewName;
        private TextView textViewStatus;
        private TextView textViewMobile;
        private CardView cardView;

        private ExerciseViewHolder(View itemView, ExercisesAdapter adapter) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewStatus = (TextView) itemView.findViewById(R.id.textViewStatus);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int pos = getAdapterPosition();
            if (pos >= 0) {
                Toast.makeText(mActivity, "Selected Item Position "+pos, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
