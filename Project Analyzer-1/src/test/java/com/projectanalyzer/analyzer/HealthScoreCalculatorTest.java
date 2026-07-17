package com.projectanalyzer.analyzer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthScoreCalculatorTest {

    private final HealthScoreCalculator calculator = new HealthScoreCalculator();

    @Test
    void calculateOverallHealthScore_ValidScores() {
        // Quality = 87 (25% = 21.75)
        // Security = 74 (25% = 18.5)
        // Architecture = 91 (20% = 18.2)
        // Testing = 76 (15% = 11.4)
        // Dependencies = 90 (15% = 13.5)
        // Total = 83.35 -> rounded to 83.4
        double score = calculator.calculateOverallHealthScore(87.0, 74.0, 91.0, 76.0, 90.0);
        assertEquals(83.4, score);
    }
}
