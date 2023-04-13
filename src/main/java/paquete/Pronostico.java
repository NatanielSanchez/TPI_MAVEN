package paquete;

/**
 * Representa una apuesta o predicción hacia un equipo de un partido jugado.
 */
public class Pronostico
{
    private final Partido partido; // El partido particular en donde jugó el equipo
    private final Equipo equipo; // El equipo al que se apuesta
    private final ResultadoEnum resultado; // La prediccion. Puede ser "ganador", "perdedor" o "empate"

    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado)
    {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
    }

    /**
     * Determina si la predicción fue acertada
     * @return true si la predicción fue acertada, false de lo contrario.
     */
    public boolean acierto()
    {
        ResultadoEnum x = partido.confirmarResultado(equipo); //ver confirmarResultado() en Partido
        if (x.equals(resultado))
            return true;

        return false;
    }

    public Partido getPartido()
    {
        return partido;
    }

    public Equipo getEquipo()
    {
        return equipo;
    }

    public ResultadoEnum getResultado()
    {
        return resultado;
    }

    /**
     * Devuelve la id del partido al que se hizo la apuesta.
     * @return ID de un partido.
     */
    public int getIdPartido()
    {
        return partido.getId();
    }

    @Override
    public String toString()
    {
        if (resultado == ResultadoEnum.EMPATE)
            return partido.toString() + "\n\tPREDICCION: " + resultado + "\n";

        return partido.toString() + "\n\tPREDICCION: " + equipo.toString() + " - " + resultado + "\n";
    }

}
