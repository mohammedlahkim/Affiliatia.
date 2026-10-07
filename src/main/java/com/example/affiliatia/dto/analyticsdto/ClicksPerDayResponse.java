package com.example.affiliatia.dto.analyticsdto;

import java.time.LocalDate;

public record ClicksPerDayResponse(
        LocalDate date,
        Long clicks
) {}