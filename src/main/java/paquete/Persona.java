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
        for (Pronostico x : pronosticos)
        {
            if (x.acierto()) puntos++;
        }
        return puntos;
    }

    public String toString()
    {
        StringBuilder stb = new StringBuilder("NOMBRE: " + nombre + " - PUNTOS: " + calcularPuntos() + "\n");
        for (Pronostico x : pronosticos)
        {
            stb.append("  -PRONOSTICO DEL PARTIDO " + x.getIdPartido() + ": \n");
            stb.append(x.toString());
        }
        return stb.toString();
    }
}
