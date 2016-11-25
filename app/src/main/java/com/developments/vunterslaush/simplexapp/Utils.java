package com.developments.vunterslaush.simplexapp;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Slaush on 24/11/2016.
 */
public class Utils
{
    private static Utils instance;


    private Utils(){}
    public static Utils getInstance()
    {
        if(instance == null)
            instance = new Utils();
        return instance;
    }

    public void addTextWatcher(final EditText editText)
    {
        TextWatcher textWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                try
                {
                    new Ecuacion(editable.toString());
                    editText.setTextColor(Color.GREEN);
                }
                catch (EcuacionNoValida ecuacionNoValida)
                {
                    editText.setTextColor(Color.RED);
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
    }
}
