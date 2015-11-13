package anton.krupnov.akka.actors;

import akka.actor.UntypedActor;
import anton.krupnov.akka.messages.ProcessLine;
import anton.krupnov.akka.messages.ReadFile;

import java.io.File;

public class FileParser extends UntypedActor {

  @Override
  public void onReceive(Object o) throws Exception {
    if (o instanceof ReadFile) {
      ReadFile readFile = (ReadFile) o;
      System.out.println("FileParser. Parse file: " + readFile.getFile());
      parseFile(readFile.getFile());
    } else {
      unhandled(o);
    }
  }

  private void parseFile(File file) {
    for (int i = 0; i < 10; i++) {
      getSender().tell(new ProcessLine("\"1;27,17\";\"1;27,17\";"));
    }
  }

}
