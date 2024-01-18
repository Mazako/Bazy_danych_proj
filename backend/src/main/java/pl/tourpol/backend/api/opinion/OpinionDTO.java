package pl.tourpol.backend.api.opinion;

import pl.tourpol.backend.persistance.entity.Opinion;
import pl.tourpol.backend.persistance.view.FullOpinionInfo;

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

    static OpinionDTO toDto(FullOpinionInfo fullOpinionInfo) {
        return new OpinionDTO(
                fullOpinionInfo.getOpinionId(),
                fullOpinionInfo.getRate(),
                fullOpinionInfo.getSendDate(),
                fullOpinionInfo.getComment()
        );
    }
}
