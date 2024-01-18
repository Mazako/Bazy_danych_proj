package pl.tourpol.backend.api.opinion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.tourpol.backend.persistance.entity.Contract;
import pl.tourpol.backend.persistance.entity.Opinion;
import pl.tourpol.backend.persistance.repository.ContractRepository;
import pl.tourpol.backend.persistance.repository.OpinionRepository;
import pl.tourpol.backend.persistance.view.FullOpinionInfo;
import pl.tourpol.backend.security.exception.RequestErrorMessage;
import pl.tourpol.backend.security.exception.RequestException;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperation;
import pl.tourpol.backend.security.permissions.AccessSensitiveOperationType;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public class OpinionService {

    private final OpinionRepository opinionRepository;
    private final ContractRepository contractRepository;
    private final AccessSensitiveOperation accessSensitiveOperation;

    public OpinionService(OpinionRepository opinionRepository, ContractRepository contractRepository, AccessSensitiveOperation accessSensitiveOperation) {
        this.opinionRepository = requireNonNull(opinionRepository);
        this.contractRepository = requireNonNull(contractRepository);
        this.accessSensitiveOperation = requireNonNull(accessSensitiveOperation);
    }

    public Page<OpinionDTO> getOpinionsByResortId(long resortId, int page){
        Page<FullOpinionInfo> opinions = opinionRepository.findAllByResortId(resortId, PageRequest.of(page, 4));
        if (opinions.isEmpty())
            return Page.empty();
        return opinions.map(OpinionDTO::toDto);
    }

    public boolean isOpinionAdded(long contractId) {
        return accessSensitiveOperation.callWithAccessCheck(contractId,
                () -> opinionRepository.isOpinionAdded(contractId),
                AccessSensitiveOperationType.CONTRACT_ACCESS);
    }

    public Opinion addOpinion(long contractId, short rate, String comment) {
        return accessSensitiveOperation.callWithAccessCheck(contractId,
                () -> addOpinionWrapper(contractId, rate, comment),
                AccessSensitiveOperationType.CONTRACT_ACCESS);
    }
    private Opinion addOpinionWrapper(long contractId, short rate, String comment) {
        if (rate < 0 || rate > 5) {
            throw new RequestException(RequestErrorMessage.INVALID_RATE);
        }
        Contract contract = contractRepository.findById(contractId).get();
        if (contract.getStatus() != Contract.Status.DONE) {
            throw new RequestException(RequestErrorMessage.CONTRACT_IS_NOT_DONE);
        }
        return opinionRepository.save(new Opinion(rate, comment, LocalDate.now(), contract));
    }


}
