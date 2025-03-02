package by.innowise.moviereview.service.impl;

import by.innowise.moviereview.dto.PersonCreateDto;
import by.innowise.moviereview.dto.PersonDto;
import by.innowise.moviereview.dto.PersonFilter;
import by.innowise.moviereview.entity.Person;
import by.innowise.moviereview.enums.MovieRole;
import by.innowise.moviereview.exception.NotFoundException;
import by.innowise.moviereview.mapper.PersonMapper;
import by.innowise.moviereview.repository.PersonRepository;
import by.innowise.moviereview.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public Page<PersonDto> getPeopleWithFiltersAndPagination(PersonFilter personFilter) {
        MovieRole role = null;
        if (personFilter.getRole() != null && !personFilter.getRole().isEmpty()) {
            try {
                role = MovieRole.valueOf(personFilter.getRole());
            } catch (IllegalArgumentException e) {
                throw new NotFoundException(String.format("Роли %s не найдено", personFilter.getRole()));
            }
        }
        Page<Person> personPage = personRepository.findWithFilters(personFilter.getSearch(), role, PageRequest.of(personFilter.getPage() - 1, personFilter.getSize()));
        return personPage.map(personMapper::toDto);
    }

    @Override
    public List<PersonDto> getPeople(MovieRole movieRole) {
        List<Person> people = personRepository.findAll()
                .stream()
                .filter(person -> person.getRole().equals(movieRole))
                .toList();
        return personMapper.toListDto(people);
    }

    @Override
    public PersonDto addPerson(PersonCreateDto dto) {
        checkRole(dto);
        Person entity = personMapper.toEntity(dto);
        Person person = personRepository.save(entity);
        log.info("Star {} added", person.getFullName());
        return personMapper.toDto(person);
    }

    @Override
    public PersonDto update(Long id, PersonCreateDto dto) {
        checkRole(dto);
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Человек с ID %d не найден", id)));
        entity
                .setFullName(dto.getFullName())
                .setRole(MovieRole.valueOf(dto.getRole()));
        Person person = personRepository.save(entity);
        PersonDto personDto = personMapper.toDto(person);
        log.info("Star with ID {} has been changed", id);
        return personDto;
    }

    @Override
    public void deletePersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Человек с ID %d не найден", id)));
        personRepository.delete(person);
        log.info("Star with ID {} has been deleted", id);
    }

    private void checkRole(PersonCreateDto dto) {
        if (!dto.getRole().isEmpty()) {
            try {
                MovieRole.valueOf(dto.getRole());
            } catch (IllegalArgumentException e) {
                throw new NotFoundException(String.format("Роли %s не найдено", dto.getRole()));
            }
        } else throw new NotFoundException(String.format("Роли %s не найдено", dto.getRole()));

    }
}
