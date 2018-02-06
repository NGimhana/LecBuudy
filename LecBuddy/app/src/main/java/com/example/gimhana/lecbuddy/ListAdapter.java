package com.example.gimhana.lecbuddy;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gimhana on 10/25/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public static final String DESCRIPTION = "";
    public static ArrayList<Module> moduleList = new ArrayList<>();

    public  ListAdapter(ArrayList<Module> moduleList){
        ListAdapter.moduleList = moduleList;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.modulecard, parent, false);
        ViewHolder vh = new ViewHolder(cardview);

        return vh;
    }


    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        Module module = moduleList.get(position);
        holder.bindCard(module);

    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        ImageView LectureNoteImage;
        TextView Module_description_Title;
        private Module module;

        public ViewHolder(View itemView) {
            super(itemView);
            LectureNoteImage= (ImageView) itemView.findViewById(R.id.LectureNoteImage);
            Module_description_Title= (TextView) itemView.findViewById(R.id.Module_description_Title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Module m = moduleList.get(getPosition());
                    Log.i("TAG", "Clicked " + m.getModuleDiscription());
                    Intent i = new Intent(v.getContext(), MainNoteActivity.class);
                    i.putExtra(DESCRIPTION, m.getModuleDiscription());
                    v.getContext().startActivity(i);
                }
            });
        }
        public void bindCard(Module module) {
            this.module = module;
            //LectureNoteImage.setImageResource(card.getImage());
            Module_description_Title.setText(module.getModuleDiscription());
        }


    }
}
