package anton.krupnov.akka.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import anton.krupnov.akka.messages.Finish;
import anton.krupnov.akka.messages.IdAmount;

import java.util.HashMap;
import java.util.Map;

public class Aggregator extends UntypedActor {

  private static ActorRef AGGREGATOR;
  private static final Map<Integer, Double> ID_AMOUNT = new HashMap<Integer, Double>();

  private static int expectedNumberOfLines = -1;
  private static int actualNumberOfLines = 0;

  private Aggregator() { }

  public static void setAggregator(ActorRef actorRef) {
    if (AGGREGATOR == null) {
      AGGREGATOR = actorRef;
    }
  }

  public static ActorRef getAggregator() {
    if (AGGREGATOR == null) {
      throw new IllegalArgumentException();
    }
    return AGGREGATOR;
  }

  @Override
  public void onReceive(Object o) throws Exception {
    if (o instanceof IdAmount) {
      IdAmount idAmount = (IdAmount) o;
      System.out.println("Aggregator. For id: " + idAmount.getId() + " add amount: " + idAmount.getAmount());
      process(idAmount.getId(), idAmount.getAmount());
    } else if (o instanceof Finish) {
      Finish finish = (Finish) o;
      addFinishInfo(finish);
    } else {
      unhandled(o);
    }
  }

  private void addFinishInfo(Finish finish) {
    System.out.println("Finish info: " + finish.getFinishType() + "-" + finish.getNumberOf());
    switch (finish.getFinishType()) {
      case FILE:
        expectedNumberOfLines = finish.getNumberOf();
        break;
      case LINE:
        actualNumberOfLines++;
        break;
    }

    if (expectedNumberOfLines >= 0 && expectedNumberOfLines == actualNumberOfLines) {
      printFinalResultsAndExit();
    }

    System.out.println("Finish results: " + expectedNumberOfLines + "=" + actualNumberOfLines);
  }

  private void printFinalResultsAndExit() {
    System.out.println("\n\nFinal results:");
    for (Map.Entry<Integer, Double> entry : ID_AMOUNT.entrySet()) {
      System.out.println("ID: " + entry.getKey() + ", total value: " + entry.getValue());
    }
    System.exit(0);
  }

  private void process(int id, double amount) {
    Double value = ID_AMOUNT.get(id);
    if (value == null) {
      value = 0.0;
    }
    value += amount;
    ID_AMOUNT.put(id, value);
  }

}
