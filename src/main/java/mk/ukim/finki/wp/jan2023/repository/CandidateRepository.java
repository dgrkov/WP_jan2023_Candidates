package mk.ukim.finki.wp.jan2023.repository;

import mk.ukim.finki.wp.jan2023.model.Candidate;
import mk.ukim.finki.wp.jan2023.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByGenderEquals(Gender gender);
    List<Candidate> findByDateOfBirthBefore(LocalDate date);
    List<Candidate> findByGenderEqualsAndDateOfBirthBefore(Gender gender, LocalDate date);
}
