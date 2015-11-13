package anton.krupnov.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import anton.krupnov.akka.actors.Aggregator;
import anton.krupnov.akka.actors.FileParser;
import anton.krupnov.akka.actors.Finisher;
import anton.krupnov.akka.actors.LineProcessor;
import anton.krupnov.akka.messages.ReadFile;

public class Starter {

  public static void main(String[] args) {

    ActorSystem system = ActorSystem.create("PiSystem");
    system.actorOf(new Props(Finisher.class), "listener");
    system.actorOf(new Props(LineProcessor.class), "lineProcessor");
    system.actorOf(new Props(Aggregator.class), "aggregator");
    ActorRef fileParser = system.actorOf(new Props(FileParser.class), "fileParser");

    fileParser.tell(new ReadFile("file"));
  }
}
