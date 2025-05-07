package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo;
  private List<Movimiento> movimientos;

  private ValidadorDeposito validadorDeposito;
  private ValidadorExtraccion validadorExtraccion;

  public Cuenta() {
    this(0);
  }

  public Cuenta(double montoInicial) {
    this.saldo = montoInicial;
    this.movimientos = new ArrayList<>();
    this.validadorDeposito = new ValidadorDeposito();
    this.validadorExtraccion = new ValidadorExtraccion();
  }

  public void poner(double cuanto) {
    if (cuanto <= 0) throw new MontoNegativoException(cuanto + ": el monto debe ser positivo");
    validadorDeposito.validar(this, cuanto, LocalDate.now());

    movimientos.add(new Movimiento(LocalDate.now(), cuanto, true));
    saldo += cuanto;
  }

  public void sacar(double cuanto) {
    if (cuanto <= 0) throw new MontoNegativoException(cuanto + ": el monto debe ser positivo");
    validadorExtraccion.validar(this, cuanto, LocalDate.now());

    movimientos.add(new Movimiento(LocalDate.now(), cuanto, false));
    saldo -= cuanto;
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return movimientos.stream()
        .filter(mov -> !mov.isDeposito() && mov.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }
}
