package anton.krupnov.akka.actors;

import akka.actor.UntypedActor;
import anton.krupnov.akka.messages.Finish;
import anton.krupnov.akka.messages.IdAmount;

import java.util.HashMap;
import java.util.Map;

public class Aggregator extends UntypedActor {

  private static final Map<Integer, Double> ID_AMOUNT = new HashMap<Integer, Double>();
  private int count;

  @Override
  public void onReceive(Object o) throws Exception {
    if (o instanceof IdAmount) {
      IdAmount idAmount = (IdAmount) o;
      System.out.println("Aggregator. For id: " + idAmount.getId() + " add amount: " + idAmount.getAmount());
      process(idAmount.getId(), idAmount.getAmount());
    } else {
      unhandled(o);
    }
  }

  private void process(int id, double amount) {
    Double value = ID_AMOUNT.get(id);
    if (value == null) {
      value = 0.0;
    }
    value += amount;
    ID_AMOUNT.put(id, value);
    count++;
    if (count >= 20) {
      sender().tell(new Finish());
    }
  }

}
