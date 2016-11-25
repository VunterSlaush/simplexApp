package com.developments.vunterslaush.simplexapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TabFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String STEP_TAG = "step";
    SolutionStep step;

    public TabFragment()
    {
        // Required empty public constructor
    }


    public static TabFragment newInstance(SolutionStep step)
    {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_TAG, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            step = getArguments().getParcelable(STEP_TAG);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        TextView title = (TextView) container.findViewById(R.id.titleFragmentTab);
        TextView operacion = (TextView)container.findViewById(R.id.operacionTextView);
        title.setText("Paso:");
        operacion.setText(step.toString());
        return inflater.inflate(R.layout.fragment_tab, container, false);

    }

}
