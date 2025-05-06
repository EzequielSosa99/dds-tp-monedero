package dds.monedero.model;

import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;

public class ValidadorExtraccion {

  public void validar(Cuenta cuenta, double cuanto, LocalDate fecha) {
    if (cuenta.getSaldo() < cuanto) {
      throw new SaldoMenorException("No puede sacar mas de " + cuenta.getSaldo() + " $");
    }

    double extraidoHoy = cuenta.getMontoExtraidoA(fecha);
    double disponible = 1000 - extraidoHoy;

    if (cuanto > disponible) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000 + " diarios, " + "límite: " + disponible);
    }
  }
}
