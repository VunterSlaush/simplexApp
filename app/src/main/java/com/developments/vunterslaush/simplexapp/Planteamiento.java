package com.developments.vunterslaush.simplexapp;

import android.content.Context;

import com.developments.vunterslaush.simplexapp.Ecuacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
  Clase Planteamiento: Es una clase que Representa el Planteamiento Completo
  					   de un problema de Programacion Lineal, esto quiere decir
  					   que tiene la Funcion objetivo y todas y cada una de las
  					   Restricciones dentro de si, formando asi el planteamiento
  					   de un problema de programacion Linea.
  					   Ademas es la encargada de estandarizar todas las restricciones

  Atributos:

  	private Ecuacion funcionObj: La ecuacion Referente a la Funcion Objetivo

	private ArrayList<Ecuacion> restricciones: Lista de Objetos Ecuaciones
											   que tiene todas y cada una
											   de las restricciones del
											   problema.

  Metodos:

  	public Planteamiento (String funcionObjetivo, ArrayList<String> rest)
	throws PlanteamientoNoValido
								   Constructor principal que Recibe el String
								   que representa la Ecuacion de la funcion
								   Objetivo y una lista de Strings que
								   representan todas las restricciones del
								   problema, esta clase se encarga en convertir
								   todos los Strings a objetos Ecuaciones,
								   y ademas Evalua la posible ocurrencia de algun
								   error.
								   Recibe:
								   Cadena de la Funcion Objetivo
								   Lista de cadenas de las Restricciones

	private void convertirEnPlanteamiento (String fObjetivo, ArrayList<String> rest)
	throws PlanteamientoNoValido
								  Metodo encargado de tomar
								  la cadena de la funcion Objetivo
								  y la lista de cadenas de las restric-
								  ciones y convertirlas todas a objetos
								  ecuaciones, estandarizandolas mediante
								  las reglas Preliminares de un problema
								  de programacion Lineal.
								  Recibe:
								  Cadena de la Funcion Objetivo
								  Lista de cadenas de las Restricciones



	private Boolean noSoloUnaFuncionObjetivo(): Metodo encargado de preguntar
												en todas las restricciones
												si son funciones Objetivos
												con el fin de Evaluar si existe
												una funcion objetivo en las
												restricciones para evaluar
												dicho Error.

	private void comprobarVariables()
	throws PlanteamientoNoValido      Metodo Encargado de comprobar
									  que las variables de cada restriccion
									  estan en las variables presentes
									  en la funcion objetivo, a fin de
									  encontrar la coherencia en el planteamiento.

	private Boolean compararVariables(Ecuacion fObj, ArrayList<String> rest)

									Metodo que Compara todas las Variables
									de cada una de las restricciones una a una
									para saber si existe una variable que no
									tiene coherencia con el problema.
									recibe: Ecuacion de la Funcion Objetivo
											Lista de Cadenas de Restricciones
									Retorna: true si no hay inconsitencias
											 false si encuentra 1 inconsistencia.


	public Ecuacion getFuncionObjetivo(): Retorna el objeto Ecuacion de la Funcion
										  Objetivo.

	public ArrayList<Ecuacion> getRestricciones(): Retorna la lista de Objetos
												   Ecuacion de las restricciones
												   del planteamiento.

	public String toString(): Retorna un String que representa todo el plante
							  miento escrito con su forma Estandar.


	public ArrayList<String> retornarVariables(): Retorna la lista de todas
												  las variables existentes en el
												  planteamiento, incluyendo
												  variables de holgura (s1,s2..etc)

	private ArrayList<String> removerRepetidos(ArrayList<String> listaRepetida):

											Funcion que remueve los Repetidos de
											la lista de variables (Formato) que
											sera retornada para Formatear las
											tablas y Renglones.

	public ArrayList<Ecuacion> retornarFormasEstandar(): Retorna una lista de ecuaciones
														 de todas las restricciones
														 estandarizadas.


  Fin de la Documentacion de la Clase Planteamiento.

  Fecha: Febrero 2016
  @author Jesus Mota
*/

public class Planteamiento
{
    private Ecuacion funcionObj;
    private ArrayList<Ecuacion> restricciones;
    private Context app;

    public Planteamiento(String funcionObjetivo, ArrayList<String> rest)
            throws PlanteamientoNoValido
    {
        restricciones = new ArrayList<>();
        convertirEnPlanteamiento(funcionObjetivo,rest);
        comprobarVariables();
        app = MyApplication.getInstance();
    }

    private void convertirEnPlanteamiento(String fObjetivo, ArrayList<String> rest)
            throws PlanteamientoNoValido
    {

        Ecuacion ecuacionAuxiliar = null;
        String errorMessage = null;
        int field = 0; //0 es F.O!
        try
        {
            funcionObj = new Ecuacion(fObjetivo);
        }
        catch (EcuacionNoValida ex)
        {
            errorMessage = ex.toString();
            field = 0;
        }

        if(errorMessage != null)
            throw new PlanteamientoNoValido(errorMessage,field);
        if(rest.isEmpty())
            throw new PlanteamientoNoValido(app.getString(R.string.no_restriccion),0);
        for (int i=0; i< rest.size(); i++)
        {
            try
            {
                ecuacionAuxiliar = new Ecuacion(rest.get(i));
                ecuacionAuxiliar.estandarizar(i+1);
            }
            catch(EcuacionNoValida error)
            {
                errorMessage = error.toString();
                field = i+1;
                break;
            }
            restricciones.add(ecuacionAuxiliar);
        }

        if(errorMessage != null)
            throw new PlanteamientoNoValido(errorMessage,field);
        if(noSoloUnaFuncionObjetivo())
            throw new PlanteamientoNoValido(app.getString(R.string.error_una_fo),0);
    }

    private Boolean noSoloUnaFuncionObjetivo()
    {
        for (int i=0;i<restricciones.size();i++ )
        {
            if(restricciones.get(i).esFuncionObjetivo())
                return true;
        }
        return false;
    }

    private void comprobarVariables() throws PlanteamientoNoValido
    {
        for (int i=0; i<restricciones.size(); i++)
        {
            if(!compararVariables(funcionObj,restricciones.get(i).getVariables()))
                throw new PlanteamientoNoValido(app.getString(R.string.error_variables_no_usadas),i+1);
        }
    }

    private Boolean compararVariables(Ecuacion fObj, ArrayList<String> rest)
    {
        for (int i = 0; i<rest.size() ;i++)
        {
            if(!fObj.buscarVariable(rest.get(i)))
                return false;
        }
        return true;
    }


    public Ecuacion getFuncionObjetivo()
    {
        return funcionObj;
    }
    public ArrayList<Ecuacion> getRestricciones()
    {
        return restricciones;
    }

    public String toString()
    {
        String toStrinReturn = app.getString(R.string.tu_planteamiendo);

        toStrinReturn += app.getString(R.string.func_obj)+ funcionObj.toString() + "\n\n";
        toStrinReturn += app.getString(R.string.sujeta_restricciones);

        for (int i = 0; i<restricciones.size() ;i++)
        {
            toStrinReturn += app.getString(R.string.R)+(i+1)+":"+ restricciones.get(i).toString() + "\n\n";
        }

        toStrinReturn += app.getString(R.string.forma_estandar);

        for (int i = 0; i<restricciones.size() ;i++)
        {
            toStrinReturn += app.getString(R.string.R)+(i+1)+":"+ restricciones.get(i).getEstandarString()+ "\n\n";
        }

        return toStrinReturn;
    }
    public ArrayList<String> retornarVariables()
    {
        ArrayList<String> variablesTotales = new ArrayList<String>();

        variablesTotales.addAll(funcionObj.getVariables());

        for (Ecuacion ec : restricciones)
        {
            variablesTotales.addAll(ec.getEcuacionEstandar().getVariables());
        }

        variablesTotales = removerRepetidos(variablesTotales);
        return variablesTotales;
    }

    private ArrayList<String> removerRepetidos(ArrayList<String> listaRepetida)
    {
        ArrayList<String> sinRepetidos = new ArrayList<String>();
        for (String str : listaRepetida )
        {
            if(!sinRepetidos.contains(str))
                sinRepetidos.add(str);
        }
        return sinRepetidos;
    }

    public ArrayList<Ecuacion> retornarFormasEstandar()
    {
        ArrayList<Ecuacion> formasEstandar = new ArrayList<Ecuacion>();
        for (Ecuacion ec : restricciones)
        {
            formasEstandar.add(ec.getEcuacionEstandar());
        }
        return formasEstandar;
    }
}


//Excepcion  Auxiliar para cuando El Planteamiento no es Valido!
// Esta Clase Es Creada para Mostrar Los Mensajes de
// cada uno de los posibles errores que puede ocurrir en el planteamiento.
class PlanteamientoNoValido extends Exception
{
    private String errorMessage;
    private int campo;
    PlanteamientoNoValido(String message, int campo)
    {
        super(message);
        errorMessage = message;
        this.campo = campo;
    }

    @Override
    public String toString()
    {
        return errorMessage;
    }
    public int getCampo(){return campo;}
}
