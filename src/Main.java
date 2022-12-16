import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import engine.PlatformEngine;
import inputHandler.Input;

import java.io.File;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    String inputFile = args[0];
    outputSolution(inputFile, "results.out");
  }

  public static void outputSolution(final String inputFile, final String outputFile)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Input inputData = objectMapper.readValue(new File(inputFile), Input.class);

    ArrayNode output = objectMapper.createArrayNode();

    PlatformEngine.getEngine().setInputData(inputData);
    PlatformEngine.getEngine().setOutput(output);
    PlatformEngine.getEngine().runEngine();

    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    objectWriter.writeValue(new File(outputFile), output);
  }
}
