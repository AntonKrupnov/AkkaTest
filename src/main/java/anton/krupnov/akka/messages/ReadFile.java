package anton.krupnov.akka.messages;

import java.io.File;

public class ReadFile {

  private File file;

  public ReadFile(String filePath) {
    file = new File(filePath);
  }

  public File getFile() {
    return file;
  }

}

