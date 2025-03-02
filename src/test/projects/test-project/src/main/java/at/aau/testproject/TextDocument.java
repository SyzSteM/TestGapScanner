package at.aau.testproject;

public class TextDocument extends Document {

  public TextDocument(String content) {
    super(content);
  }

  @Override
  public void display() {
    System.out.println("Text Document: " + getContent());
  }

  public void addText(String text) {
    this.content += " " + text;
  }

}
