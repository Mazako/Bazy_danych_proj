package pl.tourpol.backend.api.opinion;

import pl.tourpol.backend.persistance.entity.Opinion;

import java.time.LocalDate;

public record OpinionDTO(
        long id,
        Short rate,
        LocalDate sendDate,
        String description) {
    static OpinionDTO toDto(Opinion opinion) {
        return new OpinionDTO(
                opinion.getId(),
                opinion.getRate(),
                opinion.getSendDate(),
                opinion.getComment()
        );
    }
}
