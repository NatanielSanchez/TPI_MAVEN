package paquete;

import java.util.ArrayList;

public class Persona // consiste en un identificador "nombre" y un arreglo de sus pronosticos
{
    private final String nombre;
    private ArrayList<Pronostico> pronosticos;

    public Persona(String nombre) // creo una persona con su arreglo-lista de pronosticos
    {
        this.nombre = nombre;
        this.pronosticos = new ArrayList<Pronostico>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public ArrayList<Pronostico> getPronosticos()
    {
        return pronosticos;
    }

    public void addPronostico(Pronostico x) // añado un pronostico al arreglo-lista de pronosticos
    {
        pronosticos.add(x);
    }

    public int calcularPuntos() // calcula el total de puntos, en relacion a los aciertos de los pronosticos
    {
        int puntos = 0;
        for (int i=0; i < pronosticos.size(); i++)
        {
            if (pronosticos.get(i).acierto()) // ver acierto() en Pronostico
                puntos++;
        }
        return puntos;
    }

    public String toString()
    {
        String txt = "NOMBRE: " + nombre + " - PUNTOS: " + calcularPuntos() + "\n";
        for (int i=0; i < pronosticos.size(); i++)
        {
            //txt = txt + "  -PRONOSTICO " + Integer.toString(i+1) + ": \n";
            txt = txt + "  -PRONOSTICO DEL PARTIDO " + pronosticos.get(i).getIdPartido() + ": \n";
            txt = txt + pronosticos.get(i).toString();
        }
        return txt;
    }
}
