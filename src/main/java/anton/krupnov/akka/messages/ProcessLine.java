package anton.krupnov.akka.messages;

public class ProcessLine {

  private String line;

  public ProcessLine(String line) {
    this.line = line;
  }

  public String getLine() {
    return line;
  }

}
