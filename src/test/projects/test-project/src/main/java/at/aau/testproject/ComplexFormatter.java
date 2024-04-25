package at.aau.testproject;

public class ComplexFormatter implements Formatter {
  @Override
  public String format(String content) {
    return content.toUpperCase() + "\n***";
  }
}
