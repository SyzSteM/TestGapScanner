package at.aau.testproject.management;

import at.aau.testproject.Document;
import at.aau.testproject.SpreadsheetDocument;
import at.aau.testproject.TextDocument;
import java.util.ArrayList;
import java.util.List;

public class DocumentManager {
  private List<Document> documents = new ArrayList<>();

  public void addDocument(Document doc) {
    documents.add(doc);
  }

  public Document createTextDocument(String content) {
    Document doc = new TextDocument(content);
    addDocument(doc);
    return doc;
  }

  public Document createSpreadsheetDocument(String content) {
    Document doc = new SpreadsheetDocument(content);
    addDocument(doc);
    return doc;
  }

  public void displayAllDocuments() {
    documents.forEach(Document::display);
  }
}
