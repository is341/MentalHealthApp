package com.mental_health.Adapters;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.mental_health.Chat;
import com.mental_health.Constants;
import com.mental_health.DoctorsDirectory;
import com.mental_health.R;
import com.mental_health.ShowQuizActivity;
import com.mental_health.dataModel.DataModelDoctors;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<DataModelDoctors> items;
    private DoctorsDirectory mActivity;
    private Context mcontext;
    private boolean isDoc;

    public DoctorsAdapter(ArrayList<DataModelDoctors> data, DoctorsDirectory activity,boolean isDoc) {
        this.isDoc = isDoc;
        this.items = data;
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(mActivity);
    }

    public void addItem(DataModelDoctors result) {
        items.add(result);
    }

    public void setInflater(LayoutInflater layoutInflater){
        this.mInflater =layoutInflater;
    }

    public void replaceItems(ArrayList<DataModelDoctors> newItems) {
        this.items.clear();
        for(DataModelDoctors item: newItems)
            this.items.add(item);
    }

    public void insertItem(DataModelDoctors item) {
        items.add(0, item);
    }

    public void clearItems(){
        items.clear();
    }

    public void AddResults(ArrayList<DataModelDoctors> result) {
        items.addAll(result);
    }

    public DataModelDoctors getItemsAt(int position){
        return  items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataModelDoctors model = items.get(position);
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        Glide
                .with(mActivity)
                .load(Constants.BasrUrl+model.getImage())
                .centerCrop()
                .placeholder(R.drawable.dr_m_0)
                .into(messageViewHolder.imageViewIcon);
        messageViewHolder.textViewName.setText(model.getName());
        messageViewHolder.textViewStatus.setText(model.getStatus());
        messageViewHolder.textViewMobile.setText(model.getMobile());
        if (isDoc){
            messageViewHolder.imageQuest.setVisibility(View.VISIBLE);
            messageViewHolder.imageQuest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ShowQuizActivity.class);
                    intent.putExtra("userId",model.getId());
                    mActivity.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootCategoryView = mInflater.inflate(R.layout.row_item, parent, false);
        return new MessageViewHolder(rootCategoryView, this);
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView imageViewIcon;
        private CircleImageView imageQuest;
        private TextView textViewName;
        private TextView textViewStatus;
        private TextView textViewMobile;
        private CardView cardView;

        private MessageViewHolder(View itemView, DoctorsAdapter adapter) {
            super(itemView);
            imageQuest =  itemView.findViewById(R.id.imageQuest);
            imageViewIcon =  itemView.findViewById(R.id.imageViewIcon);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewStatus = (TextView) itemView.findViewById(R.id.textViewStatus);
            textViewMobile = (TextView) itemView.findViewById(R.id.textViewMobile);
            cardView = (CardView) itemView.findViewById(R.id.cardView1);


            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int pos = getAbsoluteAdapterPosition();

            Log.e("aabb","pos: "+pos);
            Intent intent=new Intent(mActivity, Chat.class);
            intent.putExtra("name", items.get(pos).getName());
            intent.putExtra("id", items.get(pos).getId());
            Log.e("aabb","name: "+ items.get(pos).getName());

            mActivity.startActivity(intent);

        }
    }
}
