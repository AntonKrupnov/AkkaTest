package anton.krupnov.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import anton.krupnov.akka.actors.Aggregator;
import anton.krupnov.akka.actors.FileParser;
import anton.krupnov.akka.messages.ReadFile;

public class Starter {

  public static void main(String[] args) {

    ActorSystem system = ActorSystem.create("PiSystem");

    ActorRef fileParser = system.actorOf(Props.create(FileParser.class), "fileParser");
    Aggregator.setAggregator(system.actorOf(Props.create(Aggregator.class), "aggregator"));

    fileParser.tell(new ReadFile("input.txt"), fileParser);
  }
}
