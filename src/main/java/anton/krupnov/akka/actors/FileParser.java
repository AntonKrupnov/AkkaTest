package anton.krupnov.akka.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;
import anton.krupnov.akka.messages.Finish;
import anton.krupnov.akka.messages.ProcessLine;
import anton.krupnov.akka.messages.ReadFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileParser extends UntypedActor {

  private Router router = createRouter();

  private Router createRouter() {
    ArrayList<Routee> routees = new ArrayList<Routee>();
    final Props props = Props.create(LineProcessor.class);
    for (int i = 0; i < 10; i++) {
      ActorRef actorRef = context().actorOf(props);
      context().watch(actorRef);
      routees.add(new ActorRefRoutee(actorRef));
    }
    return new Router(new SmallestMailboxRoutingLogic(), routees);
  }

  @Override
  public void onReceive(Object o) throws Exception {
    if (o instanceof ReadFile) {
      ReadFile readFile = (ReadFile) o;
      System.out.println("FileParser. Start parsing file: " + readFile.getInputStream());
      parseFile(readFile.getInputStream());
    } else {
      unhandled(o);
    }
  }

  private void parseFile(InputStream inputStream) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    int i = 0;
    String line = bufferedReader.readLine();
    while (line != null) {
      i++;
      router.route(new ProcessLine(line), self());
      line = bufferedReader.readLine();
    }

    sendFinishParsingMessage(i);
  }

  private void sendFinishParsingMessage(int i) {
    Props props = Props.create(Aggregator.class);
    ActorRef aggregator = context().actorOf(props);
    aggregator.tell(new Finish(Finish.FinishType.FILE, i), self());
  }
}
