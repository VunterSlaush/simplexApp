package com.developments.vunterslaush.simplexapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
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
    String operaciones;
    public SolutionStep(List<Renglon> renglones, String operaciones)
    {
        this.renglones = new ArrayList<>();
        addRenglones(renglones);
        this.operaciones = new String(operaciones);
    }

    public SolutionStep(Parcel in)
    {
        operaciones = in.readString();

        Object[] objects =  in.readArray(Renglon.class.getClassLoader());
        renglones = new ArrayList<>();
        for (Object o: objects)
        {
            renglones.add((Renglon)o);
        }
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

}
