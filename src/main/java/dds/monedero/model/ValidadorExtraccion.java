package dds.monedero.model;

import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;

public class ValidadorExtraccion {
  private static final double LIMITE_DIARIO = 1000;

  public void validar(Cuenta cuenta, double cuanto, LocalDate fecha) {
    if (cuenta.getSaldo() < cuanto) {
      throw new SaldoMenorException("No puede sacar mas de " + cuenta.getSaldo() + " $");
    }

    double extraidoHoy = cuenta.getMontoExtraidoA(fecha);
    double disponible = LIMITE_DIARIO  - extraidoHoy;

    if (cuanto > disponible) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + LIMITE_DIARIO + " diarios, " + "límite: " + disponible);
    }
  }
}
