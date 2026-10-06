package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.Redirect;
import com.example.affiliatia.Repository.RedirectRepository;
import com.example.affiliatia.service.RedirectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedirectServiceImpl implements RedirectService {

    private final RedirectRepository redirectRepository;

    @Override
    public Redirect findByFromPath(String fromPath) {

        return redirectRepository.findByFromPath(fromPath)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Redirect introuvable : " + fromPath
                        )
                );
    }
}