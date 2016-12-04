package com.developments.vunterslaush.simplexapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * Created by Slaush on 25/11/2016.
 * Representa Cada paso de la solucion!
 *
 */
public class SolutionStep
{
    List<Renglon> renglones;
    HashMap<String, Integer> variablesBasicas;
    String operaciones;
    int pRenglon;
    String pVariable;


    public SolutionStep()
    {
        renglones = new ArrayList<>();
    }



    public SolutionStep(List<Renglon> renglones, String operaciones, Pivote<String, Integer> pivote)
    {
        this.renglones = new ArrayList<>();
        addRenglones(renglones);
        if(operaciones != null)
            this.operaciones = new String(operaciones);
        else
            this.operaciones = "Tabla Inicial";
        pRenglon = pivote.getValue();
        pVariable = pivote.getKey();
    }


    private void addRenglones(List<Renglon> renglones)
    {

        for (Renglon r: renglones)
            this.renglones.add(new Renglon(r));
    }

    @Override
    public String toString()
    {
        return operaciones;
    }

    public List<Renglon> getRenglones() {
        return renglones;
    }

    public void setRenglones(List<Renglon> renglones)
    {
        addRenglones(renglones);
    }

    public String getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(String operaciones) {
        this.operaciones = operaciones;
    }

    public int getpRenglon() {
        return pRenglon;
    }

    public void setpRenglon(int pRenglon) {
        this.pRenglon = pRenglon;
    }

    public String getpVariable() {
        return pVariable;
    }

    public void setpVariable(String pVariable) {
        this.pVariable = pVariable;
    }

    public void setVariablesBasicas(HashMap<String,Integer> variablesBasicas) {
        this.variablesBasicas = new HashMap<>(variablesBasicas);
    }
}
