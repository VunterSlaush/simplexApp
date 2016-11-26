package com.developments.vunterslaush.simplexapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class TabFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String STEP_TAG = "step";
    SolutionStep step;
    int stepPosition;
    public TabFragment()
    {
        // Required empty public constructor
    }


    public static TabFragment newInstance(int stepPosition)
    {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(STEP_TAG, stepPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            stepPosition = getArguments().getInt(STEP_TAG);
            step = Tabla.getInstance().steps.get(stepPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        TextView title = (TextView) view.findViewById(R.id.titleFragmentTab);
        TextView operacion = (TextView)view.findViewById(R.id.operacionTextView);
        TableLayout table = (TableLayout)view.findViewById(R.id.tableLayoutTab);
        Log.d("VUNTER","/--/> Fragment onCreateView:"+stepPosition);
        title.setText("Paso:"+(stepPosition+1));
        makeTable(table);
        operacion.setText(step.toString());
        return view;

    }

    private void makeTable(TableLayout table)
    {

        table.addView(createTableHeader());
        String key = step.pVariable;

        for (int i = 0; i< step.renglones.size(); i++)
        {
            Renglon r = step.renglones.get(i);
            if(i == step.pRenglon)
                table.addView(createTableRowHighlighted(r));
            else
                table.addView(createTableRow(r));
        }

        highlightColum(table,Tabla.getInstance().positionAtFormato(key));
    }

    /**
     * TODO a;adir highlight unico para el pivote exacto!
     */
    private void highlightColum(TableLayout table, int variable)
    {
        for (int i = 1; i<table.getChildCount(); i++)
        {
            TableRow row = (TableRow) table.getChildAt(i);
            TextView tv = (TextView) row.getChildAt(variable);
            if(tv != null)
                tv.setBackgroundColor(Color.GREEN);
            else
                Log.d("VUNTER",i+"VS"+variable);
        }
    }

    private View createTableRowHighlighted(Renglon r)
    {
        TableRow v = (TableRow) createRow(r.retornarStringListOfValues());
        for (int i =0; i<v.getChildCount(); i++)
        {
            TextView tv = (TextView) v.getChildAt(i);
            tv.setBackgroundColor(Color.GREEN);
        }
        return v;
    }

    private View createTableHeader()
    {
        return createRow(Tabla.getInstance().formato);
    }

    private View createTableRow(Renglon r)
    {
        return createRow(r.retornarStringListOfValues());
    }

    private View createRow(ArrayList<String> list)
    {
        TableRow row = new TableRow(getContext());
        for (Object s :list)
        {
            TextView tv = new TextView(getContext());
            tv.setText(s.toString());
            row.addView(tv);
        }
        return row;
    }

}
