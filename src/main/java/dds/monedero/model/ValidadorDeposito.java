package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;

import java.time.LocalDate;

public class ValidadorDeposito {
  private static final int LIMITE_DEPOSITOS_DIARIOS = 3;

  public void validar(Cuenta cuenta, double cuanto, LocalDate fecha) {
    long depositosHoy = cuenta.getMovimientos().stream()
        .filter(m -> m.fueDepositado(fecha))
        .count();

    if (depositosHoy >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedió los " + LIMITE_DEPOSITOS_DIARIOS + " depósitos diarios");
    }
  }
}