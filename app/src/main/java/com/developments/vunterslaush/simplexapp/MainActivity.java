package com.developments.vunterslaush.simplexapp;

import android.app.Activity;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.crashlytics.android.Crashlytics;


import io.fabric.sdk.android.Fabric;


public class MainActivity extends Activity
{

    EditText funcionObjetivoText;
    Button simplexButton;
    Button addRestriccion;
    Button eraseAllButton;
    LinearLayout restriccionesView;
    RestricctionManager restricctionAdapter = RestricctionManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        funcionObjetivoText = (EditText)findViewById(R.id.editFuncionObjetivo);
        simplexButton = (Button)findViewById(R.id.simplexButton);
        eraseAllButton = (Button)findViewById(R.id.eraseAllButton);
        restriccionesView = (LinearLayout)findViewById(R.id.restriccionesLayout);

        Utils.getInstance().addTextWatcher(funcionObjetivoText);

        addRestriccion = (Button)findViewById(R.id.addRestriction);
        addRestriccion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                restricctionAdapter.inflateNewViewIntoLayout();
                //restricctionAdapter.notifyItemInserted(restricciones.size());
                //restricctionAdapter.notifyDataSetChanged();

                //restriccionesView.smoothScrollToPosition(restricciones.size());

            }

        });

        eraseAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                restricctionAdapter.eraseAll();
                //restricctionAdapter.notifyDataSetChanged();
                funcionObjetivoText.setText("");
            }
        });

        simplexButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    Planteamiento p = new Planteamiento(funcionObjetivoText.getEditableText().toString(),
                                                        restricctionAdapter.getStringsOfTextFields());
                    Tabla tabla = Tabla.getInstance();
                    tabla.resolver(p);
                    Crashlytics.log(Log.DEBUG,"VUNTER",p.toString());
                    Crashlytics.log(Log.DEBUG,"VUNTER","/--/> Solucion:"+tabla.solucionFactible());

                    Intent intent = new Intent(MainActivity.this,TabsActivity.class);
                    startActivity(intent);
                }
                catch (PlanteamientoNoValido planteamientoNoValido)
                {
                    Crashlytics.log(Log.DEBUG,"VUNTER",planteamientoNoValido.toString());
                    evaluarError(planteamientoNoValido);
                } catch (SinSolucionFactible sinSolucionFactible)
                {
                    funcionObjetivoText.setError(sinSolucionFactible.toString());
                }
            }
        });

        restricctionAdapter.initManager(MainActivity.this, restriccionesView);

        initAdd();

    }

    private void initAdd()
    {

    }


    private void evaluarError(PlanteamientoNoValido error)
    {

        borrarErroresPrevios();

        if(error.getCampo() == 0) {
            funcionObjetivoText.requestFocus();
            funcionObjetivoText.setError(error.toString());

        }
        else
            setErrorToConstraintN(error);
    }

    private void borrarErroresPrevios()
    {
        funcionObjetivoText.setError(null);
        restricctionAdapter.removeErrors();
    }

    private void setErrorToConstraintN(PlanteamientoNoValido error)
    {
        restricctionAdapter.setErrorToConstraint(error);
    }

}
