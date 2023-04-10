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
        for (Partido y : partidos)
        {
            if (y.getId() == num)
            {
                x = y;
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
        StringBuilder stb = new StringBuilder("- RONDA " + numero + ": " + "\n");
        for (Partido x : partidos)
        {
            stb.append("  *PARTIDO " + x.getId() + ": \n");
            stb.append(x.toString());
            stb.append("\n");
        }
        return stb.toString();
    }
}
