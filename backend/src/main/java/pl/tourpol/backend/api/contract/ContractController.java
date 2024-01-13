package pl.tourpol.backend.api.contract;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static pl.tourpol.backend.api.contract.ContractService.ContractDTO;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = requireNonNull(contractService);
    }

    @GetMapping
    public ResponseEntity<List<ContractDTO>> getContracts() {
        List<ContractDTO> contracts = contractService.getAllContracts();
        return contracts != null ? ResponseEntity.ok(contracts) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<ContractDTO> addContract(@RequestBody AddContractRequestDTO dto) {
        return ResponseEntity.ok(contractService.addContract(dto.tourId, dto.roomIds));
    }

    public record AddContractRequestDTO(long tourId, List<Long> roomIds) {}
}
