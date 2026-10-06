package com.example.affiliatia.service;

import com.example.affiliatia.Entity.Redirect;

public interface RedirectService {

    Redirect findByFromPath(String fromPath);
}