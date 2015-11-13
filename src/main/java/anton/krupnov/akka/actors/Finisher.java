package anton.krupnov.akka.actors;

import akka.actor.UntypedActor;
import anton.krupnov.akka.messages.Finish;

public class Finisher extends UntypedActor{

  @Override
  public void onReceive(Object o) throws Exception {
    if (o instanceof Finish) {
      System.out.println("Shutdown");
      getContext().system().shutdown();
    } else {
      unhandled(o);
    }
  }

}
