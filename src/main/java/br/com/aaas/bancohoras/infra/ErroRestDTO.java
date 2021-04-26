package br.com.aaas.bancohoras.infra;

public class ErroRestDTO {
  private String id;
  private String message;
  private String field;
  private String detail;

  public ErroRestDTO() {
  }

  public ErroRestDTO(String message) {
    this.message = message;
  }

  public ErroRestDTO(String id, String message) {
    this(message);
    this.id = id;
  }

  public ErroRestDTO(String id, String message, String field, String detail) {
    this(id, message);
    this.field = field;
    this.detail = detail;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
