package paquete;

import java.util.ArrayList;

/**
 * Representa un participante pronosticador.
 */
public class Persona
{
    private final String nombre;
    private ArrayList<Pronostico> pronosticos;

    public Persona(String nombre)
    {
        this.nombre = nombre;
        this.pronosticos = new ArrayList<Pronostico>();
    }

    /**
     * Inserta un pronostico x a la lista de pronosticos de la persona
     * @param x Pronostico a insertar.
     */
    public void addPronostico(Pronostico x) // añado un pronostico al arreglo-lista de pronosticos
    {
        pronosticos.add(x);
    }

    public String getNombre()
    {
        return nombre;
    }

    /**
     * Devuelve la lista entera de pronosticos.
     * @return Lista de pronosticos de una persona.
     */
    public ArrayList<Pronostico> getPronosticos()
    {
        return pronosticos;
    }

    /**
     * Busca un pronostico, según la id del partido, y lo devuelve si lo encuentra
     * en la lista de pronosticos de la persona. De lo contrario, devuelve null.
     * @param id ID de partido a buscar.
     * @return Un pronostico, si encuentra uno con la id de partido, o null si no se encuentra.
     */
    public Pronostico getPronostico(int id)
    {
        for (Pronostico x : pronosticos)
        {
            if (id == x.getIdPartido()) return x;
        }
        return null;
    }

    public String toString()
    {
        StringBuilder stb = new StringBuilder(Color.CYAN + "NOMBRE: " + nombre + Color.RESET + "\n");
        for (Pronostico x : pronosticos)
        {
            stb.append("  -PRONOSTICO DEL PARTIDO " + x.getIdPartido() + ": \n");
            stb.append(x.toString());
        }
        return stb.toString();
    }
}
