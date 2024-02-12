package mk.ukim.finki.wp.jan2023.service.impl;

import mk.ukim.finki.wp.jan2023.model.Party;
import mk.ukim.finki.wp.jan2023.model.exceptions.InvalidPartyIdException;
import mk.ukim.finki.wp.jan2023.repository.PartyRepository;
import mk.ukim.finki.wp.jan2023.service.PartyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyServiceImpl implements PartyService {
    private final PartyRepository repository;

    public PartyServiceImpl(PartyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Party findById(Long id) {
        return repository.findById(id).orElseThrow(InvalidPartyIdException::new);
    }

    @Override
    public List<Party> listAll() {
        return repository.findAll();
    }

    @Override
    public Party create(String name) {
        Party party = new Party(name);
        return repository.save(party);
    }
}
