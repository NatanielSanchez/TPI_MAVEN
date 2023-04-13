package paquete;

import java.util.ArrayList;

/**
 * Representa un conjunto de rondas.
 */
public class Fase
{
    private final int numero;
    private ArrayList<Ronda> rondas;

    public Fase(int numero)
    {
        this.numero = numero;
        this.rondas = new ArrayList<>();
    }

    /**
     * Inserta una ronda a la fase.
     * @param x Ronda a insertar.
     */
    public void addRonda(Ronda x)
    {
        rondas.add(x);
    }

    public int getNumero()
    {
        return numero;
    }

    /**
     * Devuelve la lista de rondas de la fase.
     * @return Lista de rondas.
     */
    public ArrayList<Ronda> getRondas()
    {
        return rondas;
    }

    @Override
    public String toString()
    {
        StringBuilder stb = new StringBuilder("FASE " + numero + ":\n");
        for (Ronda x : rondas)
        {
            stb.append(x.toString());
        }
        return stb.toString();
    }
}
