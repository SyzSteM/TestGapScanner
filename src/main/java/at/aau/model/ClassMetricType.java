package at.aau.model;


public enum ClassMetricType implements MetricType {
  WMC,
  DIT,
  FIN,
  FOUT,
  LOC,
  NOF,
  NOC,
  NOM,
  CBO,
  RFC,
  SOC,
  REVS;

  @Override
  public String getName() {
    return name();
  }
}
