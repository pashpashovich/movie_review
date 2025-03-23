package by.innowise.moviereview.service.impl;

import by.innowise.moviereview.dto.EntityCreateDto;
import by.innowise.moviereview.dto.EntityDto;
import by.innowise.moviereview.dto.GenreFilterDto;
import by.innowise.moviereview.entity.Genre;
import by.innowise.moviereview.exception.NotFoundException;
import by.innowise.moviereview.mapper.GenreMapper;
import by.innowise.moviereview.repository.GenreRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreServiceImpl genreService;

    private Genre genre;
    private EntityDto entityDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genre = new Genre(1L, "Action", null);
        entityDto = new EntityDto(1L, "Action");
    }

    @Test
    void shouldReturnListOfGenres() {
        // given
        when(genreRepository.findAll()).thenReturn(List.of(genre));
        when(genreMapper.toListDto(List.of(genre))).thenReturn(List.of(entityDto));
        //when
        List<EntityDto> result = genreService.findAll();
        //then
        assertEquals(1, result.size());
        verify(genreRepository).findAll();
        verify(genreMapper).toListDto(List.of(genre));
    }

    @Test
    void shouldReturnListOfNames() {
        // given
        when(genreRepository.findAll()).thenReturn(List.of(genre));
        //when
        List<String> names = genreService.getGenres();
        //then
        assertEquals(List.of("Action"), names);
    }

    @Test
    void shouldCreateNewGenre() {
        // given
        EntityCreateDto createDto = new EntityCreateDto("Action");

        when(genreRepository.findByName("Action")).thenReturn(Optional.empty());
        when(genreMapper.toCreateEntity(createDto)).thenReturn(genre);
        when(genreRepository.save(genre)).thenReturn(genre);
        when(genreMapper.toDto(genre)).thenReturn(entityDto);
        //when
        EntityDto saved = genreService.save(createDto);
        //then
        assertEquals(entityDto, saved);
        verify(genreRepository).save(genre);
    }

    @Test
    void shouldThrowExceptionIfGenreExists() {
        // given
        when(genreRepository.findByName("Action")).thenReturn(Optional.of(genre));
        //when
        //then
        EntityCreateDto dto = new EntityCreateDto("Action");
        assertThrows(EntityExistsException.class, () -> genreService.save(dto));
    }

    @Test
    void shouldUpdateGenre() {
        // given
        EntityCreateDto updateDto = new EntityCreateDto("Drama");
        Genre updatedGenre = new Genre(1L, "Drama", null);
        EntityDto updatedDto = new EntityDto(1L, "Drama");

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(genreRepository.save(any())).thenReturn(updatedGenre);
        when(genreMapper.toDto(updatedGenre)).thenReturn(updatedDto);
        //when
        EntityDto result = genreService.update(1L, updateDto);
        //then
        assertEquals("Drama", result.getName());
    }

    @Test
    void update_ShouldThrowNotFoundException() {
        // given
        when(genreRepository.findById(999L)).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(NotFoundException.class, () -> genreService.update(999L, new EntityCreateDto("Drama")));
    }

    @Test
    void shouldDeleteGenre() {
        // given
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        //when
        genreService.delete(1L);
        //then
        verify(genreRepository).delete(genre);
    }

    @Test
    void shouldThrowNotFoundException() {
        // given
        when(genreRepository.findById(999L)).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(NotFoundException.class, () -> genreService.delete(999L));
    }

    @Test
    void shouldReturnPagedGenres() {
        // given
        GenreFilterDto filter = new GenreFilterDto("", "id",1, 10);
        Page<Genre> page = new PageImpl<>(List.of(genre));
        when(genreRepository.findAllWithFilters("", PageRequest.of(0, 10, Sort.by("id")))).thenReturn(page);
        when(genreMapper.toListDto(List.of(genre))).thenReturn(List.of(entityDto));
        //when
        Map<String, Object> result = genreService.getGenresWithFilters(filter);
        //then
        assertEquals(1, ((List<?>) result.get("genres")).size());
        assertEquals(1, result.get("currentPage"));
    }
}
