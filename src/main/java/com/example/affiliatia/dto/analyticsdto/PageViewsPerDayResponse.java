package com.example.affiliatia.dto.analyticsdto;

import java.time.LocalDate;

public record PageViewsPerDayResponse(
        LocalDate date,
        Long views
) {}