package com.afj.solution.buyitapp.service;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.model.TemporaryToken;
import com.afj.solution.buyitapp.repository.TemporaryTokenRepository;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class TemporaryTokenServiceImpl implements TemporaryTokenService {
    private final TemporaryTokenRepository temporaryTokenRepository;

    @Autowired
    public TemporaryTokenServiceImpl(final TemporaryTokenRepository temporaryTokenRepository) {
        this.temporaryTokenRepository = temporaryTokenRepository;
    }

    @Override
    public TemporaryToken save() {
        log.info("Save temporary token to the database");
        return temporaryTokenRepository.save(new TemporaryToken());
    }

    @Override
    public boolean isTokenExist(final UUID id) {
        log.info("Check if the token {} exist in the database", id);
        return temporaryTokenRepository.findById(id).isPresent();
    }
}
