package at.aau.model;


public enum MethodMetricType implements MetricType {
  CBOM,
  CBO,
  RFC,
  WMC,
  FOUT,
  FIN;

  @Override
  public String getName() {
    return name();
  }
}
