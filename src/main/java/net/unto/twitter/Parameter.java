package net.unto.twitter;

final class Parameter
{
  private String name = null;
  
  private Object value = null;
  
  protected Parameter(String name, Object value) {
    this.name = name;
    this.value = value;
  }
  
  public String getName() {
    return name;
  }
  
  public boolean hasName() {
    return name != null;
  }
  
  public Object getValue() {
    return value;
  }
  
  public boolean hasValue() {
    return value != null;
  }
}
