package at.aau.testproject.management;

import at.aau.testproject.Document;

public class ReportGenerator {
  public static String generateContentReport(Document document) {
    return "Report on Document: \nContent Length: "
        + document.getContent().length()
        + " characters";
  }
}
