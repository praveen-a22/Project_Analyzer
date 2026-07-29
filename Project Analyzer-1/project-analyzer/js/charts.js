/**
 * Project Analyzer — Chart.js Integrations
 * Configures all chart widgets with customized dark SaaS visual styles.
 */

const AppCharts = (function() {
  // Common Chart Defaults for Dark Theme
  const chartDefaults = {
    color: '#94A3B8',
    font: {
      family: "'Plus Jakarta Sans', sans-serif",
      size: 11
    },
    plugins: {
      legend: {
        labels: {
          color: '#F8FAFC',
          font: { family: "'Plus Jakarta Sans', sans-serif", weight: '600', size: 12 },
          usePointStyle: true,
          padding: 16
        }
      },
      tooltip: {
        backgroundColor: '#151B2B',
        titleColor: '#F8FAFC',
        bodyColor: '#94A3B8',
        borderColor: '#232D42',
        borderWidth: 1,
        padding: 12,
        cornerRadius: 8,
        displayColors: true
      }
    }
  };

  return {
    // 1. Project Health Line Chart
    initHealthLineChart: function(canvasId) {
      const ctx = document.getElementById(canvasId);
      if (!ctx) return;

      const gradient = ctx.getContext('2d').createLinearGradient(0, 0, 0, 300);
      gradient.addColorStop(0, 'rgba(99, 102, 241, 0.4)');
      gradient.addColorStop(1, 'rgba(99, 102, 241, 0.0)');

      new Chart(ctx, {
        type: 'line',
        data: {
          labels: ['Run 1', 'Run 2', 'Run 3', 'Run 4', 'Run 5', 'Run 6', 'Latest'],
          datasets: [{
            label: 'Project Health Score',
            data: [72, 75, 73, 79, 81, 83, 84],
            borderColor: '#6366F1',
            borderWidth: 3,
            backgroundColor: gradient,
            fill: true,
            tension: 0.35,
            pointBackgroundColor: '#8B5CF6',
            pointBorderColor: '#080B14',
            pointBorderWidth: 2,
            pointRadius: 5,
            pointHoverRadius: 7
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: chartDefaults.plugins,
          scales: {
            x: {
              grid: { color: 'rgba(255, 255, 255, 0.05)', drawBorder: false },
              ticks: { color: '#94A3B8' }
            },
            y: {
              min: 50,
              max: 100,
              grid: { color: 'rgba(255, 255, 255, 0.05)', drawBorder: false },
              ticks: { color: '#94A3B8', stepSize: 10 }
            }
          }
        }
      });
    },

    // 2. Issue Severity Doughnut Chart
    initIssueDoughnutChart: function(canvasId) {
      const ctx = document.getElementById(canvasId);
      if (!ctx) return;

      new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: ['Critical (3)', 'High (8)', 'Medium (17)', 'Low (12)'],
          datasets: [{
            data: [3, 8, 17, 12],
            backgroundColor: ['#EF4444', '#F59E0B', '#6366F1', '#06B6D4'],
            borderColor: '#151B2B',
            borderWidth: 3,
            hoverOffset: 6
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            ...chartDefaults.plugins,
            legend: {
              position: 'bottom',
              labels: chartDefaults.plugins.legend.labels
            }
          },
          cutout: '72%'
        }
      });
    },

    // 3. Code Quality Radar Chart
    initQualityRadarChart: function(canvasId) {
      const ctx = document.getElementById(canvasId);
      if (!ctx) return;

      new Chart(ctx, {
        type: 'radar',
        data: {
          labels: ['Security', 'Maintainability', 'Testability', 'Architecture', 'Performance', 'Reliability'],
          datasets: [{
            label: 'Current Build Score',
            data: [74, 87, 76, 91, 89, 83],
            borderColor: '#8B5CF6',
            backgroundColor: 'rgba(139, 92, 246, 0.25)',
            borderWidth: 2,
            pointBackgroundColor: '#6366F1',
            pointRadius: 4
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: chartDefaults.plugins,
          scales: {
            r: {
              angleLines: { color: 'rgba(255, 255, 255, 0.08)' },
              grid: { color: 'rgba(255, 255, 255, 0.08)' },
              pointLabels: { color: '#F8FAFC', font: { size: 11, weight: '600' } },
              ticks: { color: '#94A3B8', backdropColor: 'transparent' }
            }
          }
        }
      });
    },

    // 4. Test Coverage Doughnut Chart
    initTestCoverageChart: function(canvasId) {
      const ctx = document.getElementById(canvasId);
      if (!ctx) return;

      new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: ['Covered (76%)', 'Uncovered (24%)'],
          datasets: [{
            data: [76, 24],
            backgroundColor: ['#22C55E', '#232D42'],
            borderColor: '#151B2B',
            borderWidth: 4
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            ...chartDefaults.plugins,
            legend: { position: 'bottom', labels: chartDefaults.plugins.legend.labels }
          },
          cutout: '75%'
        }
      });
    },

    // 5. Dependency Status Bar Chart
    initDependencyBarChart: function(canvasId) {
      const ctx = document.getElementById(canvasId);
      if (!ctx) return;

      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: ['Up To Date', 'Outdated', 'Vulnerable'],
          datasets: [{
            label: 'Libraries',
            data: [33, 6, 3],
            backgroundColor: ['#22C55E', '#F59E0B', '#EF4444'],
            borderRadius: 6,
            barThickness: 32
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: chartDefaults.plugins,
          scales: {
            x: {
              grid: { display: false },
              ticks: { color: '#94A3B8' }
            },
            y: {
              grid: { color: 'rgba(255, 255, 255, 0.05)' },
              ticks: { color: '#94A3B8' }
            }
          }
        }
      });
    }
  };
})();
