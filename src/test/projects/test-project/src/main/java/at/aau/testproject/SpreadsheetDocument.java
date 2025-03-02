package at.aau.testproject;

public class SpreadsheetDocument extends Document {

  public SpreadsheetDocument(String content) {
    super(content);
  }

  @Override
  public void display() {
    System.out.println("Spreadsheet Document: " + getContent());
  }

  public void addData(String data) {
    this.content += ", " + data;
  }

}
