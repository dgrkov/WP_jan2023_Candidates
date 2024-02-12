package mk.ukim.finki.wp.jan2023.service.impl;

import mk.ukim.finki.wp.jan2023.model.Candidate;
import mk.ukim.finki.wp.jan2023.model.Gender;
import mk.ukim.finki.wp.jan2023.model.Party;
import mk.ukim.finki.wp.jan2023.model.exceptions.InvalidCandidateIdException;
import mk.ukim.finki.wp.jan2023.repository.CandidateRepository;
import mk.ukim.finki.wp.jan2023.service.CandidateService;
import mk.ukim.finki.wp.jan2023.service.PartyService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository repository;
    private final PartyService partyService;

    public CandidateServiceImpl(CandidateRepository repository, PartyService partyService) {
        this.repository = repository;
        this.partyService = partyService;
    }

    @Override
    public List<Candidate> listAllCandidates() {
        return repository.findAll();
    }

    @Override
    public Candidate findById(Long id) {
        return repository.findById(id).orElseThrow(InvalidCandidateIdException::new);
    }

    @Override
    public Candidate create(String name, String bio, LocalDate dateOfBirth, Gender gender, Long party) {
        Party parties = partyService.findById(party);
        Candidate candidate = new Candidate(name, bio, dateOfBirth, gender, parties);
        return repository.save(candidate);
    }

    @Override
    public Candidate update(Long id, String name, String bio, LocalDate dateOfBirth, Gender gender, Long party) {
        Party parties = partyService.findById(party);
        Candidate candidate = findById(id);
        candidate.setName(name);
        candidate.setBio(bio);
        candidate.setDateOfBirth(dateOfBirth);
        candidate.setGender(gender);
        candidate.setParty(parties);
        return repository.save(candidate);
    }

    @Override
    public Candidate delete(Long id) {
        Candidate candidate = findById(id);
        repository.delete(candidate);
        return candidate;
    }

    @Override
    public Candidate vote(Long id) {
        Candidate candidate = findById(id);
        candidate.setVotes(candidate.getVotes() + 1);
        return repository.save(candidate);
    }


    /**
     * The implementation of this method should use repository implementation for the filtering.
     *
     * @param yearsMoreThan that is used to filter the candidates who are older than this value.
     *                        This param can be null, and is not used for filtering in this case.
     * @param gender        Used for filtering the candidates gender.
     *                        This param can be null, and is not used for filtering in this case.
     * @return The candidates that meet the filtering criteria
     */
    @Override
    public List<Candidate> listCandidatesYearsMoreThanAndGender(Integer yearsMoreThan, Gender gender) {
        if (yearsMoreThan == null && gender == null) {
            return listAllCandidates();
        } else if (yearsMoreThan == null) {
            return repository.findByGenderEquals(gender);
        } else if (gender == null) {
            return repository.findByDateOfBirthBefore(LocalDate.now().minusYears(yearsMoreThan));
        } else {
            return repository.findByGenderEqualsAndDateOfBirthBefore(gender, LocalDate.now().minusYears(yearsMoreThan));
        }
    }
}
