/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import engine.PlatformEngine;
import input.Input;

import java.io.File;
import java.io.IOException;

public final class Main {
  private Main() { }

  /**
   * Get the input file from the first arg and output the solution for it.
   *
   * @param args from the command line
   * @throws IOException in case of exceptions to reading / writing
   */
  public static void main(final String[] args) throws IOException {
    String inputFile = args[0];
    outputSolution(inputFile, "results.out");
  }

  /**
   * Read the data from the inputFile and put the required output in the outputFile.
   *
   * @param inputFile JSON file to read from
   * @param outputFile file to write results to
   * @throws IOException in case of exceptions to reading / writing
   */
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
