package at.aau.testproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TextDocumentTest {

  @Test
  void addText() {
    var doc = new TextDocument("Initial");

    doc.addText("text");

    assertEquals(
        "Initial text",
        doc.getContent(),
        "Text should be added correctly to the document content."
    );
  }

}
