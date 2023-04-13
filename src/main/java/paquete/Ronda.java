package paquete;

import java.util.ArrayList;

/**
 * Representa un conjunto de Partidos
 */
public class Ronda
{
    private final int numero;
    private ArrayList<Partido> partidos; // arreglo-lista de clase Partido


    public Ronda() // NO SE USA ESTO
    {
        numero = -1;
    }

    public Ronda(int numero)
    {
        this.numero = numero;
        this.partidos = new ArrayList<Partido>();
    }

    /**
     * Inserta un partido a la ronda.
     * @param x Partido a insertar.
     */
    public void addPartido(Partido x)
    {
        partidos.add(x);
    }

    /**
     * Devuelve el numero de la ronda.
     * @return Numero de ronda.
     */
    public int getNumero()
    {
        return numero;
    }

    /**
     * Devuelve toda la lista de partidos de la ronda.
     * @return ArrayList de partidos.
     */
    public ArrayList<Partido> getPartidos()
    {
        return partidos;
    }

    /**
     * Devuelve un puntero de UN SOLO partido segun su numero ID si se encuentra en la ronda.
     * De lo contrario devuelve null
     * @param id ID de partido a buscar.
     * @return Un Partido si se encuentra uno con la id, o null de lo conrario.
     */
    public Partido getPartido(int id)
    {
        Partido x = null;
        for (Partido y : partidos)
        {
            if (y.getId() == id)
            {
                x = y;
                break;
            }
        }
        return x;
    }

    public int getSize() // devuelve la cantidad de partidos de la ronda
    {
        return partidos.size();
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
