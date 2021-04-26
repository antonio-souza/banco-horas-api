package br.com.aaas.bancohoras.infra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@SuppressWarnings("deprecation")
public class RelatorioExporter implements Serializable {

  private static final long serialVersionUID = 1L;
  
  public ByteArrayOutputStream exportPdfToStream(RelatorioDTO relatorioDTO) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    relatorioDTO.getExporterParameters().put(JRExporterParameter.OUTPUT_STREAM, outputStream);
    exportPdf(relatorioDTO);
    return outputStream;
  }
  
  private void exportPdf(RelatorioDTO relatorioDTO) {
    JasperPrint jp = getJasperPrint(relatorioDTO);
    try {
      JRPdfExporter pdf = new JRPdfExporter();
      relatorioDTO.getExporterParameters().put(JRExporterParameter.JASPER_PRINT, jp);
      pdf.setParameters(relatorioDTO.getExporterParameters());
      pdf.exportReport();
    } catch (JRException e) {
      throw new RuntimeException(e);
    }
  }
  
  public JasperPrint getJasperPrint(RelatorioDTO relatorioDTO) {
    try {
      return getJasperPrintFromJasper(relatorioDTO, false);
    } catch (JRException e) {
      throw new RuntimeException(e);
    }
  }
  
  private JasperPrint getJasperPrintFromJasper(RelatorioDTO relatorioDTO, boolean jrxml) throws JRException {
    try (InputStream is = getClass().getResourceAsStream(relatorioDTO.getFilename())) {
      if (is == null) {
        throw new RuntimeException("Arquivo " + relatorioDTO.getFilename() + " não encontrado");
      }
      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(relatorioDTO.getData());
      
      if (jrxml) {
        JasperReport jr = JasperCompileManager.compileReport(is);
        return JasperFillManager.fillReport(jr, relatorioDTO.getParameters(), ds);
      }
      
      return JasperFillManager.fillReport(is, relatorioDTO.getParameters(), ds);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
