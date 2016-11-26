package com.developments.vunterslaush.simplexapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Samuel on 18/09/15
 */
public class RestricctionAdapter extends RecyclerView.Adapter<RestricctionAdapter.ViewHolder>
{
    ArrayList <Editable> editables;
    private ArrayList<String> restricciones;

    public void eraseAll()
    {
        editables.clear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public ImageButton quitButton;
        public EditText restrictionEditTex;
        public TextView textView;
        public ViewHolder(View v)
        {
            super(v);
            quitButton = (ImageButton) v.findViewById(R.id.quitRestrictionButton);
            restrictionEditTex = (EditText)v.findViewById(R.id.restriccionEditText);
            textView = (TextView)v.findViewById(R.id.restriccionTextView);
        }
    }



    public RestricctionAdapter(ArrayList<String> restricciones)
    {
        this.restricciones = restricciones;
        editables = new ArrayList<>();
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
            holder.restrictionEditTex.requestFocus();
            Utils.getInstance().addTextWatcher(holder.restrictionEditTex);
            holder.quitButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    editables.remove(holder.restrictionEditTex.getText());
                    restricciones.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());

                }
            });
            editables.add(position,holder.restrictionEditTex.getText());

        }
    }

    public ArrayList<String> getStringsOfTextFields()
    {
        ArrayList<String> strings = new ArrayList<>();
        for (Editable editable: editables)
        {
            strings.add(editable.toString());
        }
        return  strings;
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