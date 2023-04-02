package paquete;

public class Pronostico // consiste en una apuesta o prediccion hacia un equipo en un partido
{
    private final Partido partido; // El partido particular en donde jugó el equipo
    private final int idPartido;
    private final Equipo equipo; // El equipo al que se apuesta
    private final ResultadoEnum resultado; // La prediccion. Puede ser "ganador", "perdedor" o "empate"

    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado)
    {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
        this.idPartido = partido.getId();
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

    public int getIdPartido()
    {
        return idPartido;
    }

    @Override
    public String toString()
    {
        if (resultado == ResultadoEnum.EMPATE)
            return partido.toString() + "\n\tPREDICCION: " + resultado + "\n";

        return partido.toString() + "\n\tPREDICCION: " + equipo.toString() + " - " + resultado + "\n";
    }

    public boolean acierto() //determina si la prediccion fue acertada
    {
        ResultadoEnum x = partido.confirmarResultado(equipo); //ver confirmarResultado() en Partido
        if (x.equals(resultado))
            return true;

        return false;
    }
}
