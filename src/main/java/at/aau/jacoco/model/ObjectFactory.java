//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2024.04.12 at 03:57:27 PM CEST
//

package at.aau.jacoco.model;

import jakarta.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * at.aau.model package.
 *
 * <p>An ObjectFactory allows you to programatically construct new instances of the Java
 * representation for XML content. The Java representation of XML content can consist of schema derived interfaces and
 * classes representing the binding of schema type definitions, element declarations and model groups. Factory methods
 * for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
   * at.aau.model
   */
  public ObjectFactory() {}

  /**
   * Create an instance of {@link Package }
   */
  public Package createPackage() {
    return new Package();
  }

  /**
   * Create an instance of {@link Class }
   */
  public Class createClass() {
    return new Class();
  }

  /**
   * Create an instance of {@link Sourcefile }
   */
  public Sourcefile createSourcefile() {
    return new Sourcefile();
  }

  /**
   * Create an instance of {@link Counter }
   */
  public Counter createCounter() {
    return new Counter();
  }

  /**
   * Create an instance of {@link Method }
   */
  public Method createMethod() {
    return new Method();
  }

  /**
   * Create an instance of {@link SessionInfo }
   */
  public SessionInfo createSessioninfo() {
    return new SessionInfo();
  }

  /**
   * Create an instance of {@link Line }
   */
  public Line createLine() {
    return new Line();
  }

  /**
   * Create an instance of {@link Report }
   */
  public Report createReport() {
    return new Report();
  }

  /**
   * Create an instance of {@link Group }
   */
  public Group createGroup() {
    return new Group();
  }

}
