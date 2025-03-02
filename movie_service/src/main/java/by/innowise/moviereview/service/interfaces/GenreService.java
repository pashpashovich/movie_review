package by.innowise.moviereview.service.interfaces;

import by.innowise.moviereview.dto.EntityCreateDto;
import by.innowise.moviereview.dto.EntityDto;
import by.innowise.moviereview.dto.GenreFilterDto;

import java.util.List;
import java.util.Map;

public interface GenreService {
    List<EntityDto> findAll();
    Map<String, Object> getGenresWithFilters(GenreFilterDto filter);
    EntityDto save(EntityCreateDto entityCreateDto);
    EntityDto update(Long id, EntityCreateDto dto);
    List<String> getGenres();
    void delete(Long id);
}
