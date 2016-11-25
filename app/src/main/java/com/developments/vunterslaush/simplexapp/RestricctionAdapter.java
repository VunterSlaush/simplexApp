package com.developments.vunterslaush.simplexapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Samuel on 18/09/15
 */
public class RestricctionAdapter extends RecyclerView.Adapter<RestricctionAdapter.ViewHolder>
{

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public Button quitButton;
        public EditText restrictionEditTex;
        public TextView textView;
        public ViewHolder(View v)
        {
            super(v);
            quitButton = (Button) v.findViewById(R.id.quitRestrictionButton);
            restrictionEditTex = (EditText)v.findViewById(R.id.restriccionEditText);
            textView = (TextView)v.findViewById(R.id.restriccionTextView);
        }
    }


    private ArrayList<String> restricciones;

    public RestricctionAdapter(ArrayList<String> restricciones)
    {
        this.restricciones = restricciones;
    }

    @Override
    public RestricctionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restriccion_item, parent, false);

        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        if(position < restricciones.size())
        {
            holder.textView.setText("R"+(position+1)+":");
            holder.restrictionEditTex.setText(restricciones.get(position));
            Utils.getInstance().addTextWatcher(holder.restrictionEditTex,restricciones.get(position));
            holder.quitButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    restricciones.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });

        }
    }

    @Override
    public int getItemCount()
    {

        if(restricciones != null)
        {

            return restricciones.size();
        }
        else
        {
            return 0;
        }
    }
}