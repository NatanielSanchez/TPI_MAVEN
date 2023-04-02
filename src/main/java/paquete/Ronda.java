package paquete;

import java.util.ArrayList;

public class Ronda
{
    private static int totalRondas = 0; // cantidad total de rondas creadas
    private final int numero;
    private ArrayList<Partido> partidos; // arreglo-lista de clase Partido


    public Ronda() // NO SE USA ESTO
    {
        numero = -1;
        totalRondas++;
    }

    public Ronda(int numero)
    {
        this.numero = numero;
        this.partidos = new ArrayList<Partido>();
        totalRondas++;
    }

    public static int getTotalRondas()
    {
        return totalRondas;
    }

    public int getNumero()
    {
        return numero;
    }

    public ArrayList<Partido> getPartidos() // Devuelve TODA LA LISTA DE PARTIDOS
    {
        return partidos;
    }

    public Partido getPartido(int num) // Devuelve un puntero de UN SOLO partido segun su numero ID...
    {                                  // De lo contrario, devuelve null.
        Partido x = null;
        for (int i=0; i < partidos.size(); i++) // Recorre cada partido de la ronda
        {
            if (partidos.get(i).getId() == num)
            {
                x = partidos.get(i);
                break;
            }
        }
        return x;
    }

    public void setPartidos(ArrayList<Partido> partidos)
    {
        this.partidos = partidos;
    }

    public int getSize() // devuelve la cantidad de partidos de la ronda
    {
        return partidos.size();
    }

    public void addPartido(Partido x) // añade un partido a la ronda
    {
        partidos.add(x);
    }

    @Override
    public String toString()
    {
        String txt =  "- RONDA " + numero + ": " + "\n";
        for (int i=0; i < partidos.size(); i++)
        {
            txt = txt + ("  *PARTIDO " + Integer.toString(i+1) + ": \n");
            txt = txt + partidos.get(i).toString();
            txt = txt + "\n";
        }
        return txt;
    }
}
