package anton.krupnov.akka.messages;

public class IdAmount {

  private int id;
  private double amount;

  public IdAmount(int id, double amount) {
    this.id = id;
    this.amount = amount;
  }

  public int getId() {
    return id;
  }

  public double getAmount() {
    return amount;
  }

}
