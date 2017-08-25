package com.example.ha.yogabeginner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ha.yogabeginner.Utitls.ActivityUtitls;
import com.example.ha.yogabeginner.R;
import com.example.ha.yogabeginner.ViewExercise;
import com.example.ha.yogabeginner.interfaces.ItemClickListener;
import com.example.ha.yogabeginner.model.Exercise;

import java.util.List;

/**
 * Created by Ha Truong on 8/24/2017.
 * This is a YogaBeginner
 * into the com.example.ha.yogabeginner.adapter
 */

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView image;
    public TextView text;

    private ItemClickListener itemClickListener;
    public RecyclerViewHolder(View view) {
        super(view);
        image = (ImageView) view.findViewById(R.id.ex_img);
        text = (TextView) view.findViewById(R.id.ex_name);
        view.setOnClickListener(this);
    }

    public RecyclerViewHolder setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<Exercise> exerciseList;
    private Context context;

    public RecyclerViewAdapter(List<Exercise> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_excersise, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.image.setImageResource(exerciseList.get(position).getImage_id());
        holder.text.setText(exerciseList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call new Activity
                //Toast.makeText(context, exerciseList.get(position).getImage_id()+ " " + exerciseList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ViewExercise.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra(ActivityUtitls.IMAGE_PUT_EXTRA, exerciseList.get(position).getImage_id());
                intent.putExtra(ActivityUtitls.NAME_EXERCISE_EXTRA, exerciseList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
