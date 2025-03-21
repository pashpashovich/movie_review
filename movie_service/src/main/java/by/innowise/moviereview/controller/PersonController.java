package by.innowise.moviereview.controller;

import by.innowise.moviereview.dto.PersonCreateDto;
import by.innowise.moviereview.dto.PersonDto;
import by.innowise.moviereview.dto.PersonFilter;
import by.innowise.moviereview.enums.MovieRole;
import by.innowise.moviereview.service.interfaces.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<Page<PersonDto>> getPeople(@ModelAttribute @Valid PersonFilter filter) {
        Page<PersonDto> peoplePage = personService.getPeopleWithFiltersAndPagination(filter);
        return ResponseEntity.ok(peoplePage);
    }

    @GetMapping("/actors")
    public ResponseEntity<List<PersonDto>> getActors() {
        List<PersonDto> peoplePage = personService.getPeople(MovieRole.ACTOR);
        return ResponseEntity.ok(peoplePage);
    }

    @GetMapping("/directors")
    public ResponseEntity<List<PersonDto>> getDirectors() {
        List<PersonDto> peoplePage = personService.getPeople(MovieRole.DIRECTOR);
        return ResponseEntity.ok(peoplePage);
    }

    @GetMapping("/producers")
    public ResponseEntity<List<PersonDto>> getProducers() {
        List<PersonDto> peoplePage = personService.getPeople(MovieRole.PRODUCER);
        return ResponseEntity.ok(peoplePage);
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@RequestBody @Valid PersonCreateDto personCreateDto) {
        PersonDto personDto = personService.addPerson(personCreateDto);
        return ResponseEntity.ok(personDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Long id, @RequestBody @Valid PersonCreateDto personCreateDto) {
        PersonDto updatedPerson = personService.update(id, personCreateDto);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") @NotNull Long id) {
        personService.deletePersonById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

