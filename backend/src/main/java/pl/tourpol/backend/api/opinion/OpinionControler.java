package pl.tourpol.backend.api.opinion;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.requireNonNull;

@RestController
class OpinionControler {

    private final OpinionService opinionService;

    public OpinionControler(OpinionService opinionService) {
        this.opinionService = requireNonNull(opinionService);
    }

    @GetMapping("public/api/opinions")
    ResponseEntity<Page<OpinionDTO>> getOpinionsByResortId(@RequestParam long resortId,
                                                                  @RequestParam int page) {
        Page<OpinionDTO> allOpinions = opinionService.getOpinionsByResortId(resortId, page);
        return !allOpinions.isEmpty() ? ResponseEntity.ok(allOpinions) : ResponseEntity.notFound().build();
    }

    @PostMapping("/api/opinions/add")
    ResponseEntity<Long> addOpinion(@RequestBody AddOpinionRequestDTO dto) {
        return ResponseEntity.ok(opinionService.addOpinion(dto.contractId, dto.rate, dto.comment).getId());
    }

    @GetMapping("/api/opinions/added")
    ResponseEntity<Boolean> isOpinionAdded(@RequestParam long contractId) {
        return ResponseEntity.ok(opinionService.isOpinionAdded(contractId));
    }

    public record AddOpinionRequestDTO(long contractId, short rate, String comment) {}
}
