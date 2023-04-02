package paquete;

public class Partido // un partido entre dos equipos
{
    private final int id;
    private Equipo equipo1;
    private Equipo equipo2;
    private int golesEquipo1;
    private int golesEquipo2;

    public Partido()
    {
        this.id = -1;
    }

    public Partido(int id, Equipo a, Equipo b, int x, int y)
    {
        this.id = id;
        this.equipo1 = a;
        this.equipo2 = b;
        this.golesEquipo1 = x;
        this.golesEquipo2 = y;
    }

    public int getId()
    {
        return id;
    }

    public Equipo getEquipo1()
    {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1)
    {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2()
    {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2)
    {
        this.equipo2 = equipo2;
    }

    public int getGolesEquipo1()
    {
        return golesEquipo1;
    }

    public void setGolesEquipo1(int golesEquipo1)
    {
        this.golesEquipo1 = golesEquipo1;
    }

    public int getGolesEquipo2()
    {
        return golesEquipo2;
    }

    public void setGolesEquipo2(int golesEquipo2)
    {
        this.golesEquipo2 = golesEquipo2;
    }

    public String toString()
    {
        return "\tEquipo 1: " + equipo1.toString() + " - Goles: " + golesEquipo1 + "\n" +
                "\tEquipo 2: " + equipo2.toString() + " - Goles: " + golesEquipo2;
    }

    public ResultadoEnum confirmarResultado(Equipo x) throws NullPointerException
    // Equipo x representa el equipo seleccionado en un pronostico
    {
        if (x == null) throw new NullPointerException("Equipo es null. Algo salio mal...");

        ResultadoEnum res = null;
        if (golesEquipo1 == golesEquipo2)
        {
            res = ResultadoEnum.EMPATE;
            return res;
        }
        else
        {
            if (x.equals(equipo1))
            {
                if (golesEquipo1 > golesEquipo2)
                    res = ResultadoEnum.GANADOR;
                else
                    res = ResultadoEnum.PERDEDOR;
            }
            else
            {
                if (x.equals(equipo2))
                {
                    if (golesEquipo1 > golesEquipo2)
                        res = ResultadoEnum.PERDEDOR;
                    else
                        res = ResultadoEnum.GANADOR;
                }
            }
        }
        return res;
    }
}
