package at.aau.testproject;

public abstract class Document {
  protected String content;

  public Document(String content) {
    this.content = content;
  }

  public abstract void display();

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
