package at.aau.testproject;

public class Printer {

  private Formatter formatter;

  public Printer(Formatter formatter) {
    this.formatter = formatter;
  }

  public void print(Document document) {
    System.out.println(formatter.format(document.getContent()));
  }

}
