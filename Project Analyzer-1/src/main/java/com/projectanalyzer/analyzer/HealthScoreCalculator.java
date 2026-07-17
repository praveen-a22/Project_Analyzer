package com.projectanalyzer.analyzer;

import org.springframework.stereotype.Component;

@Component
public class HealthScoreCalculator {

    /**
     * Weighted Health Score Formula:
     * Code Quality = 25%
     * Security = 25%
     * Architecture = 20%
     * Testing = 15%
     * Dependencies = 15%
     */
    public double calculateOverallHealthScore(
            double codeQualityScore,
            double securityScore,
            double architectureScore,
            double testingScore,
            double dependencyScore
    ) {
        double weightedScore = (codeQualityScore * 0.25)
                + (securityScore * 0.25)
                + (architectureScore * 0.20)
                + (testingScore * 0.15)
                + (dependencyScore * 0.15);

        return Math.round(weightedScore * 10.0) / 10.0;
    }
}
