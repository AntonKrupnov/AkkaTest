package anton.krupnov.akka.actors;

import akka.actor.UntypedActor;
import anton.krupnov.akka.messages.Finish;
import anton.krupnov.akka.messages.IdAmount;
import anton.krupnov.akka.messages.ProcessLine;

public class LineProcessor extends UntypedActor {

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
    // cut off first symbol " and last symbols " and ;
    line = line.substring(1, line.length() - 2);
    String[] strings = line.split("\";\"");
    for (String string : strings) {
      String[] split = string.split(";");
      Aggregator.getAggregator().tell(new IdAmount(Integer.valueOf(split[0]), Double.valueOf(split[1])), self());
    }
    Aggregator.getAggregator().tell(new Finish(Finish.FinishType.LINE, strings.length), self());
  }
}
