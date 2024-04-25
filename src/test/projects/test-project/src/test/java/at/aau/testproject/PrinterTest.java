package at.aau.testproject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class PrinterTest {

  @Test
  void print() {
    Formatter formatter = mock(Formatter.class);
    Document doc = new TextDocument("Hello World");
    when(formatter.format(doc.getContent())).thenReturn("FORMATTED: Hello World");

    Printer printer = new Printer(formatter);
    printer.print(doc);

    verify(formatter).format("Hello World");
  }
}
