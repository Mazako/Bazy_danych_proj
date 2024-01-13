package pl.tourpol.backend.persistance.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.tourpol.backend.persistance.PopularityEntry;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PopularityReportRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PopularityReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<PopularityEntry> generatePopularityReport(LocalDate startDate, LocalDate endDate, int page, int size) {
        int offset = page * size;
        return jdbcTemplate.query(
                "SELECT * FROM generate_popularity_report(?, ?, ?, ?)",
                (rs, rowNum) -> new PopularityEntry(
                        rs.getLong("id"),
                        rs.getString("resort_name"),
                        rs.getLong("signed_contracts"),
                        rs.getLong("persons"),
                        rs.getDouble("total_profit")
                ),
                startDate, endDate, offset, size);
    }
}
