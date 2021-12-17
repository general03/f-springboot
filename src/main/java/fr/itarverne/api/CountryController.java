package fr.itarverne.api;


import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import fr.itarverne.api.CountryRepository;
import javax.validation.Valid;

@RestController
class CountryController {

  private final CountryRepository repository;

  CountryController(CountryRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/countries")
  List<Country> all() {
    return repository.findAll();
  }

  @GetMapping("/country/{id}")
  Country one(@PathVariable Long id) {
    return repository.findById(id).orElse(new Country("ERROR", "Impossible d'obtenir le pays portant l'identifiant "+id));
  }

  @PostMapping("/country")
  Country newCountry(@Valid @RequestBody Country newCountry) {
    return repository.save(newCountry);
  }

  @PutMapping("/country/{id}")
  Country replaceCountry(@RequestBody Country newCountry, @PathVariable Long id) {
    return repository.findById(id)
      .map(Country -> {
        Country.setName(newCountry.getName());
        Country.setCode(newCountry.getCode());
        return repository.save(Country);
      })
      .orElseGet(() -> {
        newCountry.setId(id);
        return repository.save(newCountry);
      });
  }

  @DeleteMapping("/country/{id}")
  void deleteCountry(@PathVariable Long id) {
    repository.deleteById(id);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error) -> {
          String fieldName = ((FieldError) error).getField();
          String errorMessage = error.getDefaultMessage();
          errors.put(fieldName, errorMessage);
      });
      return errors;
  }

}