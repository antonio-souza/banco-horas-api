package br.com.aaas.bancohoras.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporterParameter;

@SuppressWarnings("deprecation")
public class RelatorioDTO {
  private String                           filename;

  private List<?>                          data               = new ArrayList<>();

  private Map<String, Object>              parameters         = new HashMap<>();

  private Map<JRExporterParameter, Object> exporterParameters = new HashMap<>();

  public RelatorioDTO(String filename, List<?> data) {
    this.filename = filename;
    this.data = data;
  }

  public String getFilename() {
    return filename;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void addParameter(String key, Object value) {
    parameters.put(key, value);
  }

  public Map<JRExporterParameter, Object> getExporterParameters() {
    return exporterParameters;
  }

  public void addExporterParameter(JRExporterParameter key, Object value) {
    exporterParameters.put(key, value);
  }

  public List<?> getData() {
    return data;
  }
}
