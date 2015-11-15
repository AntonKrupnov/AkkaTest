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
    String[] idAmountStrings = splitLinesToIdAmountStrings(line);
    for (String idAmountString : idAmountStrings) {
      IdAmount idAmount = convertToMessage(idAmountString);
      Aggregator.getAggregator().tell(idAmount, self());
    }
    Aggregator.getAggregator().tell(new Finish(Finish.FinishType.LINE, idAmountStrings.length), self());
  }

  private IdAmount convertToMessage(String idAmountString) {
    String[] split = idAmountString.split(";");
    if (split.length != 2) {
      throw new IllegalStateException("Can not parse string: " + idAmountString);
    }
    Integer id = Integer.valueOf(split[0]);
    Double amount = Double.valueOf(split[1]);
    return new IdAmount(id, amount);
  }

  private String[] splitLinesToIdAmountStrings(String line) {
    // cut off first symbol " and last symbols " and ;
    line = line.substring(1, line.length() - 2);
    return line.split("\";\"");
  }
}
