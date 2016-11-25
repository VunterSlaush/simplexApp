package com.developments.vunterslaush.simplexapp;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity
{

    EditText funcionObjetivoText;
    Button simplexButton;
    Button addRestriccion;
    Button eraseAllButton;
    RecyclerView restriccionesView;
    ArrayList<String> restricciones;
    RestricctionAdapter restricctionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        funcionObjetivoText = (EditText)findViewById(R.id.editFuncionObjetivo);
        simplexButton = (Button)findViewById(R.id.simplexButton);
        eraseAllButton = (Button)findViewById(R.id.eraseAllButton);
        restriccionesView = (RecyclerView)findViewById(R.id.restriccionesView);

        Utils.getInstance().addTextWatcher(funcionObjetivoText,null);

        addRestriccion = (Button)findViewById(R.id.addRestriction);
        addRestriccion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                restricciones.add("");
                restricctionAdapter.notifyItemInserted(restricciones.size());
            }

        });

        eraseAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restricciones.clear();
                restricctionAdapter.notifyDataSetChanged();
                funcionObjetivoText.setText("...");
            }
        });

        simplexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try
                {

                    Planteamiento p = new Planteamiento(funcionObjetivoText.getEditableText().toString(),restricciones);
                    Log.d("VUNTER",p.toString());
                }
                catch (PlanteamientoNoValido planteamientoNoValido)
                {
                    Log.e("VUNTER",planteamientoNoValido.toString());
                }
            }
        });

        restricciones = new ArrayList<>();
        restricctionAdapter = new RestricctionAdapter(restricciones);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        restriccionesView.setLayoutManager(mLayoutManager);
        restriccionesView.setAdapter(restricctionAdapter);
    }

}
