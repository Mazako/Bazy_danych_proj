package pl.tourpol.backend.persistance;

public class PopularityEntry {
    private Long id;
    private String resortName;
    private Long signedContracts;
    private Long persons;
    private Double totalProfit;
    public PopularityEntry(Long id, String resortName, Long signedContracts, Long persons, Double totalProfit) {
        this.id = id;
        this.resortName = resortName;
        this.signedContracts = signedContracts;
        this.persons = persons;
        this.totalProfit = totalProfit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResortName() {
        return resortName;
    }

    public void setResortName(String resortName) {
        this.resortName = resortName;
    }

    public Long getSignedContracts() {
        return signedContracts;
    }

    public void setSignedContracts(Long signedContracts) {
        this.signedContracts = signedContracts;
    }

    public Long getPersons() {
        return persons;
    }

    public void setPersons(Long persons) {
        this.persons = persons;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }
}

