package com.developments.vunterslaush.simplexapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Map;


public class TabFragment extends Fragment
{

    private static final String STEP_TAG = "step";
    private static final int NORMAL_LENGHT = 5;
    SolutionStep step;
    int stepPosition;
    boolean isLast;

    public TabFragment()
    {
        // Required empty public constructor
    }


    public static TabFragment newInstance(int stepPosition, boolean isLast)
    {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(STEP_TAG, stepPosition);
        args.putBoolean("Last",isLast);
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
            isLast = getArguments().getBoolean("Last",false);
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
        //table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);
        Crashlytics.log(Log.DEBUG,"VUNTER","/--/> Fragment onCreateView:"+stepPosition);


        if(isLast)
        {
            title.setText("Respuesta");
            makeTable(table);
            //add cuadrito de Solucion!
        }
        else if(step.getRenglones().size() > 0 )
        {
            title.setText("Paso:"+(stepPosition));
            makeTable(table);
        }
        else
            title.setText("Planteamiento del Problema!");


        operacion.setText(step.toString());
        return view;

    }

    private void makeTable(TableLayout table)
    {

        table.addView(createTableHeader());
        addLineToTable(table);
        String key = step.pVariable;

        for (int i = 0; i< step.renglones.size(); i++)
        {
            Renglon r = step.renglones.get(i);
            if(i % 2 == 0)
                table.addView(createTableRowHighlighted(r,i));
            else
                table.addView(createTableRow(r,i));
        }

        //highlightColum(table,Tabla.getInstance().positionAtFormato(key)+1);// Porque la R no esta en el formato!
    }

    private void addLineToTable(TableLayout table)
    {

        TableRow row = new TableRow(getContext());
        int n = Tabla.getInstance().formato.size() + 2;
        for (int i=0; i<n; i++ )
        {
            View v = new View(getContext());
            TableRow.LayoutParams pm = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1);
            pm.setMargins(0,10,0,0);//Margenes!
            v.setLayoutParams(pm);
            v.setBackgroundColor(Color.parseColor("#bdbdbd"));
            row.addView(v);
        }
        table.addView(row);
    }

    /**
     * TODO a;adir highlight unico para el pivote exacto!
     */
    private void highlightColum(TableLayout table, int variable)
    {
        for (int i = 2; i<table.getChildCount(); i++)
        {
            TableRow row = (TableRow) table.getChildAt(i);
            TextView tv = (TextView) row.getChildAt(variable);
            if(tv != null)
                tv.setBackgroundColor(Color.WHITE);
            else
                Crashlytics.log(Log.DEBUG,"VUNTER",i+"VS"+variable);
        }
    }

    private View createTableRowHighlighted(Renglon r, int c)
    {
        ArrayList<String> row = r.retornarStringListOfValues();
        row.add(0,Integer.toString(c));
        row.add(getVariableBasica(c));
        TableRow v = (TableRow) createRow(row);
        for (int i =0; i<v.getChildCount(); i++)
        {
            TextView tv = (TextView) v.getChildAt(i);
            tv.setBackgroundColor(Color.parseColor("#b3e5fc"));
        }
        return v;
    }

    /**
     * TODO Cambiar Textos Planos por Recursos Strings!
     *
     */
    private View createTableHeader()
    {
        ArrayList<String> header = new ArrayList<>();
        header.addAll(Tabla.getInstance().formato);
        header.add(0,"R");
        header.add("VB");
        return createRowToHeader(header);
    }

    private View createTableRow(Renglon r, int i)
    {
        ArrayList<String> row = r.retornarStringListOfValues();
        row.add(0,Integer.toString(i));
        row.add(getVariableBasica(i));
        return createRow(row);
    }

    private String getVariableBasica(int i)
    {
        for (Map.Entry<String, Integer> m : step.variablesBasicas.entrySet())
        {
            if(m.getValue() == i)
                return m.getKey();
        }
        return "";
    }

    private View createRow(ArrayList<String> list)
    {
        TableRow row = new TableRow(getContext());
        for (String s :list)
        {
            TextView tv = Utils.getInstance().createTextViewStyled(getContext());
            if(s.length() > NORMAL_LENGHT)
            {
                s = Utils.getInstance().reduceNumber(s);
            }
            tv.setText(s);
            tv.setPadding(5,10,5,10);
            row.addView(tv);
        }

        TableLayout.LayoutParams pm=
                new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

        row.setLayoutParams(pm);
        return row;
    }

    private View createRowToHeader(ArrayList<String> list)
    {
        TableRow row = new TableRow(getContext());
        for (Object s :list)
        {
            TextView tv = Utils.getInstance().createTextViewStyled(getContext());
            tv.setTextSize(Utils.getInstance().convertToDp(getContext(),18));
            tv.setTypeface(null, Typeface.BOLD);
            tv.setText(s.toString());
            tv.setPadding(0,0,5,3);
            row.addView(tv);
        }
        return row;
    }

}
