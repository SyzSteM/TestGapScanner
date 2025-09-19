package at.aau.jacoco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import at.aau.exception.ParserException;
import at.aau.jacoco.model.Package;
import at.aau.jacoco.model.Report;

public final class JacocoCoverageCollector {

  private JacocoCoverageCollector() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<Package> collect(Path jacocoReportPath) throws ParserException {
    Unmarshaller unmarshaller;
    SAXSource saxSource;
    Report report;

    try {
      unmarshaller = JAXBContext.newInstance(Report.class).createUnmarshaller();
    } catch (Exception e) {
      throw ParserException.withCause("Error while creating XML unmarshaller", e);
    }

    saxSource = getXmlSourceWithoutDtd(jacocoReportPath);

    try {
      report = (Report) unmarshaller.unmarshal(saxSource);
    } catch (JAXBException e) {
      throw ParserException.withCause("Error while parsing JaCoCo report", e);
    }

    return report.getPackages();
  }

  private static SAXSource getXmlSourceWithoutDtd(Path jacocoReportPath) throws ParserException {
    InputSource inputSource;

    try {
      inputSource = new InputSource(Files.newInputStream(jacocoReportPath));
    } catch (IOException e) {
      throw ParserException.withCause(
          String.format("Error while reading JaCoCo report - [path=%s]", jacocoReportPath), e
      );
    }

    return new SAXSource(getXmlReaderWithoutDtd(), inputSource);
  }

  private static XMLReader getXmlReaderWithoutDtd() throws ParserException {
    SAXParserFactory spf = SAXParserFactory.newInstance();

    try {
      spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    } catch (Exception e) {
      throw ParserException.withCause("Error while disabling DTD loading", e);
    }

    try {
      return spf.newSAXParser().getXMLReader();
    } catch (Exception e) {
      throw ParserException.withCause("Error while creating SAX (XML) parser", e);
    }
  }

}
