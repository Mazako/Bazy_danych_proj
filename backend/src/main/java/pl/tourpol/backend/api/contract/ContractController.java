package pl.tourpol.backend.api.contract;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping()
    public ResponseEntity<?> getContracts() {
        List<ContractDTO> contracts = contractService.getAllContracts();
        return contracts != null ? ResponseEntity.ok(contracts) : ResponseEntity.notFound().build();
    }
}
