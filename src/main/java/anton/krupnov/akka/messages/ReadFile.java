package anton.krupnov.akka.messages;

import java.io.InputStream;

public class ReadFile {

  private InputStream inputStream;

  public ReadFile(String filePath) {
    inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
  }

  public InputStream getInputStream() {
    return inputStream;
  }

}

