package paquete;

public class Equipo // Nombre y descripcion de un equipo
{
    private final String nombre; // identificador unico
    private final String descripcion;

    public Equipo()
    {
        this.nombre = null;
        this.descripcion = null;
    }

    public Equipo(String nombre, String descripcion)
    {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    @Override
    public String toString()
    {
        return nombre;
    }
}
