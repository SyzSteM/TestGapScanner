package at.aau;

import at.aau.model.Class;
import at.aau.model.Report;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class JacocoTest {
  @Test
  void test1() {
    try (InputStream inputStream = getClass().getResourceAsStream("/jacoco.xml")) {
      String xml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

      JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      Report report = (Report) jaxbUnmarshaller.unmarshal(new StringReader(xml));

      List<Class> classesWithUncoveredMethods =
          report.getPackages().get(0).getClasses().stream()
              .filter(
                  clazz ->
                      clazz.getMethods().stream()
                          .allMatch(
                              method ->
                                  method.getCounters().stream()
                                          .filter(
                                              counter ->
                                                  counter.getType().equalsIgnoreCase("METHOD"))
                                          .findFirst()
                                          .get()
                                          .getCovered()
                                      == 0))
              .collect(Collectors.toList());

      System.out.println();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
