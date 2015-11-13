package anton.krupnov.akka.actors;

import akka.actor.UntypedActor;
import anton.krupnov.akka.messages.IdAmount;
import anton.krupnov.akka.messages.ProcessLine;

public class LineProcessor extends UntypedActor{

  @Override
  public void onReceive(Object o) throws Exception {
    if (o instanceof ProcessLine) {
      ProcessLine processLine = (ProcessLine) o;
      System.out.println("LineProcessor. Line: " + processLine.getLine());
      processLine(processLine.getLine());
    } else {
      unhandled(o);
    }
  }

  private void processLine(String line) {
    for (int i = 0; i < 7; i++) {
      sender().tell(new IdAmount(1, 23));
    }
  }
}
