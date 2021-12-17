package fr.itarverne.api;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
class Country {

  @Id 
  @GeneratedValue
  private Long id;

  @Pattern(regexp = "^[A-Z]{2}$")
  private String code;

  private String name;

  Country() {}

  Country(String name, String code) {
    this.name = name;
    this.code = code;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getCode() {
    return this.code;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Country))
      return false;
    Country Country = (Country) o;
    return Objects.equals(this.id, Country.id) && Objects.equals(this.name, Country.name)
        && Objects.equals(this.code, Country.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.code);
  }

  @Override
  public String toString() {
    return "Country{" + "id=" + this.id + ", name='" + this.name + '\'' + ", code='" + this.code + '\'' + '}';
  }
}