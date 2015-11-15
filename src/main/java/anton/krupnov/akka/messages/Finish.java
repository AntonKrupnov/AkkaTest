package anton.krupnov.akka.messages;

public class Finish {

  public enum FinishType {
    FILE,
    LINE
  }

  private FinishType finishType;
  private int numberOf;

  public Finish(FinishType finishType, int numberOf) {

    this.finishType = finishType;
    this.numberOf = numberOf;
  }

  public FinishType getFinishType() {
    return finishType;
  }

  public int getNumberOf() {
    return numberOf;
  }

}
