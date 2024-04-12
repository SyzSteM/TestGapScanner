package at.aau;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import at.aau.model.Report;


public class JacocoTest {
  @Test
  void test1() {
    try(InputStream inputStream = getClass().getResourceAsStream("/jacoco.xml")){
      String xml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

      JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      Report report = (Report) jaxbUnmarshaller.unmarshal(new StringReader(xml));

      System.out.println();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
