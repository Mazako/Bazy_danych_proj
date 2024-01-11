package pl.tourpol.backend.api.opinion;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;
import static pl.tourpol.backend.api.opinion.OpinionService.OpinionDto;
@RestController
@RequestMapping("/api")
public class OpinionControler {

    private final OpinionService opinionService;

    public OpinionControler(OpinionService opinionService) {
        this.opinionService = requireNonNull(opinionService);
    }

    @GetMapping("opinions")
    public ResponseEntity<Page<OpinionDto>> getOpinionsByResortId(@RequestParam long resortId,
                                                                   @RequestParam int page) {
        Page<OpinionDto> allOpinions = opinionService.getOpinionsByResortId(resortId, page);
        return !allOpinions.isEmpty() ? ResponseEntity.ok(allOpinions) : ResponseEntity.notFound().build();
    }
}
