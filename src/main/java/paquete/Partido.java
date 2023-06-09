package paquete;

import java.security.InvalidParameterException;

public class Partido // un partido entre dos equipos
{
    private final int id;
    private Equipo equipo1;
    private Equipo equipo2;
    private int golesEquipo1;
    private int golesEquipo2;

    public Partido(int id, Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2)
    {
        this.id = id;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.golesEquipo1 = golesEquipo1;
        this.golesEquipo2 = golesEquipo2;
    }

    public int getId()
    {
        return id;
    }

    public Equipo getEquipo1()
    {
        return equipo1;
    }

    public Equipo getEquipo2()
    {
        return equipo2;
    }

    public int getGolesEquipo1()
    {
        return golesEquipo1;
    }

    public int getGolesEquipo2()
    {
        return golesEquipo2;
    }

    public String toString()
    {
        return equipo1.toString() + " [" + golesEquipo1 + "] | [" +
                golesEquipo2 + "] " + equipo2.toString();
    }

    /**
     * Determina el resultado de un equipo x (que jugó en el partido)
     * @param x Uno de los equipos del partido
     * @return ResultadoEnum que puede ser GANADOR, PERDEDOR o EMPATE. Devuelve null si
     * el equipo x que entra por parametro NO JUGO EL PARTIDO.
     */
    public ResultadoEnum confirmarResultado(Equipo x)
    {
        if (x == null) throw new InvalidParameterException("Equipo es null. Algo salio mal...");
        if ( ! ( x.equals(equipo1) || x.equals(equipo2) ) ) return null;

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
