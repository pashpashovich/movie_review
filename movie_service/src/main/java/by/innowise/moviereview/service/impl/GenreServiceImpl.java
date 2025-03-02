package by.innowise.moviereview.service.impl;

import by.innowise.moviereview.dto.EntityCreateDto;
import by.innowise.moviereview.dto.EntityDto;
import by.innowise.moviereview.dto.GenreFilterDto;
import by.innowise.moviereview.entity.Genre;
import by.innowise.moviereview.exception.NotFoundException;
import by.innowise.moviereview.mapper.GenreMapper;
import by.innowise.moviereview.repository.GenreRepository;
import by.innowise.moviereview.service.interfaces.GenreService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<EntityDto> findAll() {
        List<Genre> genreList = genreRepository.findAll();
        return genreMapper.toListDto(genreList);
    }
    @Override
    public Map<String, Object> getGenresWithFilters(GenreFilterDto filter) {
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), Sort.by(filter.getSort()));
        Page<Genre> entities = genreRepository.findAllWithFilters(filter.getSearch(), pageable);
        List<EntityDto> genreList = genreMapper.toListDto(entities.getContent());

        return Map.of(
                "genres", genreList,
                "totalPages", entities.getTotalPages(),
                "currentPage", filter.getPage()
        );
    }

    @Override
    public List<String> getGenres() {
        return genreRepository.findAll()
                .stream()
                .map(Genre::getName)
                .toList();
    }

    @Override
    public EntityDto save(EntityCreateDto entityCreateDto) {
        if (genreRepository.findByName(entityCreateDto.getName()).isPresent())
            throw new EntityExistsException("Такой жанр уже существует");
        Genre genre = genreMapper.toCreateEntity(entityCreateDto);
        Genre saved = genreRepository.save(genre);
        log.info("Genre {} added", genre.getName());
        return genreMapper.toDto(saved);
    }

    @Override
    public EntityDto update(Long id, EntityCreateDto dto) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Сущности с ID %d не найдено", id)));
        genre.setName(dto.getName());
        Genre genre1 = genreRepository.save(genre);
        log.info("Genre ID {} changed to {}", genre.getId(), genre.getName());
        return genreMapper.toDto(genre1);
    }

    @Override
    public void delete(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Сущности с ID %d не найдено", id)));
        genreRepository.delete(genre);
        log.info("Genre with ID {} removed", id);
    }
}

