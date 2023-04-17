package paquete;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartidoTest
{
    @Test
    @DisplayName("Debería devolver un ResultadoEnum no nulo.")
    public void shouldReturnResultadoEnum()
    {
        int id = 1;
        Equipo equipo1 = new Equipo("Los Pepegas", "lmao");
        Equipo equipo2 = new Equipo("Los Boludos", "lole");
        int goles1 = 23;
        int goles2 = 23;
        Equipo equipo3 = new Equipo ("Los Pelotudos", "lul");
        Partido partido = new Partido(id, equipo1, equipo2, goles1, goles2);
        Assertions.assertTrue(partido.confirmarResultado(equipo1) != null);
        Assertions.assertTrue(partido.confirmarResultado(equipo2) != null);
        Assertions.assertFalse(partido.confirmarResultado(equipo3) != null);
    }
}