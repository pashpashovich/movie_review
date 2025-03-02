package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.PersonCreateDto;
import by.innowise.moviereview.dto.PersonDto;
import by.innowise.moviereview.dto.PersonFilter;
import by.innowise.moviereview.enums.MovieRole;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PersonService {
    Page<PersonDto> getPeopleWithFiltersAndPagination(PersonFilter personFilter);
    List<PersonDto> getPeople(MovieRole movieRole);
    PersonDto addPerson(PersonCreateDto dto);
    PersonDto update(Long id, PersonCreateDto dto);
    void deletePersonById(Long id);
}
