package com.afj.solution.buyitapp.service;

import java.util.UUID;

import com.afj.solution.buyitapp.model.TemporaryToken;

/**
 * @author Tomash Gombosh
 */
public interface TemporaryTokenService {

    TemporaryToken save();

    boolean isTokenExist(UUID id);
}
