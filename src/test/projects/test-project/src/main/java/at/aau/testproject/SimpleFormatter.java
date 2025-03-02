package at.aau.testproject;

public class SimpleFormatter implements Formatter {

  @Override
  public String format(String content) {
    return content.toLowerCase();
  }

}
