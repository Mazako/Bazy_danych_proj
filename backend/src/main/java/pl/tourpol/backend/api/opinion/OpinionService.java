package pl.tourpol.backend.api.opinion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.tourpol.backend.persistance.entity.Opinion;
import pl.tourpol.backend.persistance.repository.OpinionRepository;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public class OpinionService {

    private final OpinionRepository opinionRepository;

    public OpinionService(OpinionRepository opinionRepository) {
        this.opinionRepository = requireNonNull(opinionRepository);
    }

    public Page<OpinionDto> getOpinionsByResortId(long resortId, int page){
        Page<Opinion> opinions = opinionRepository.findAllByResortId(resortId, PageRequest.of(page, 4));
        if (opinions.isEmpty())
            return Page.empty();
        return opinions.map(this::convertToOpinionDto);
    }

    private OpinionDto convertToOpinionDto(Opinion opinion) {
        return new OpinionDto(
                opinion.getRate(),
                opinion.getSendDate(),
                opinion.getComment()
        );
    }

    public static record OpinionDto(
            Short rate,
            LocalDate sendDate,
            String description
    ){}
}
