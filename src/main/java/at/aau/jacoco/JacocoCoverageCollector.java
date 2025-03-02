package at.aau.jacoco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import at.aau.jacoco.model.Package;
import at.aau.jacoco.model.Report;

public final class JacocoCoverageCollector {

  private JacocoCoverageCollector() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<Package> collect(Path jacocoReportPath) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    SAXSource saxSource = getXmlSourceWithoutDtd(jacocoReportPath);

    Report report = (Report) unmarshaller.unmarshal(saxSource);

    return report.getPackages();
  }

  private static SAXSource getXmlSourceWithoutDtd(Path jacocoReportPath)
      throws IOException, ParserConfigurationException, SAXException {
    InputSource inputSource = new InputSource(Files.newInputStream(jacocoReportPath));

    return new SAXSource(getXmlReaderWithoutDtd(), inputSource);
  }

  private static XMLReader getXmlReaderWithoutDtd()
      throws ParserConfigurationException, SAXException {
    SAXParserFactory spf = SAXParserFactory.newInstance();

    spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

    return spf.newSAXParser().getXMLReader();
  }

}
