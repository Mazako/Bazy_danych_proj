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
    ResponseEntity<List<ContractDTO>> getContracts() {
        List<ContractDTO> contracts = contractService.getAllContracts();
        return contracts != null ? ResponseEntity.ok(contracts) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    ResponseEntity<ContractDTO> addContract(@RequestBody AddContractRequestDTO dto) {
        return ResponseEntity.ok(contractService.addContract(dto.tourId, dto.roomIds));
    }

    @GetMapping("/checkRefund")
    ResponseEntity<PriceDTO> checkRefundPossibility(@RequestParam long contractId) {
        return ResponseEntity.ok(new PriceDTO(contractService.checkRefundPossibility(contractId)));
    }

    @DeleteMapping("/refund")
    ResponseEntity<PriceDTO> refund(@RequestParam long contractId) {
        return ResponseEntity.ok(new PriceDTO(contractService.refundContract(contractId)));
    }

    public record AddContractRequestDTO(long tourId, List<Long> roomIds) {}

    public record PriceDTO(float price) {}

}
