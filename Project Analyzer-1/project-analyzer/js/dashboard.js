/**
 * Project Analyzer — Dashboard Page Logic
 */

document.addEventListener('DOMContentLoaded', () => {
  if (document.getElementById('healthScoreCounter')) {
    initDashboardView();
  }
});

function initDashboardView() {
  // Animate KPI Numbers
  animateCounter('healthScoreCounter', 84);
  animateCounter('qualityScoreCounter', 87);
  animateCounter('securityScoreCounter', 74);
  animateCounter('coverageScoreCounter', 76);

  // Initialize Dashboard Charts
  AppCharts.initHealthLineChart('projectHealthLineChart');
  AppCharts.initIssueDoughnutChart('issueSeverityDoughnutChart');

  // Fetch recent projects table
  ApiService.getProjects().then(projects => {
    renderProjectsTable(projects);
  });
}

function renderProjectsTable(projects) {
  const tbody = document.getElementById('dashboardProjectsTbody');
  if (!tbody) return;

  const badgeMap = {
    "Excellent": "badge-excellent",
    "Healthy": "badge-healthy",
    "Good": "badge-good",
    "Warning": "badge-warning",
    "Critical": "badge-critical"
  };

  tbody.innerHTML = projects.map(proj => `
    <tr>
      <td>
        <div class="d-flex align-items-center gap-2">
          <i class="bi bi-folder-fill text-accent fs-5"></i>
          <div>
            <a href="project-details.html?id=${proj.id}" class="fw-bold text-white text-decoration-none hover-accent">${proj.name}</a>
            <div class="text-muted" style="font-size:0.75rem;">${proj.repoUrl}</div>
          </div>
        </div>
      </td>
      <td>
        <span class="tech-pill">${proj.technology}</span>
      </td>
      <td>
        <span class="badge-custom ${badgeMap[proj.healthStatus] || 'badge-good'}">
          <i class="bi bi-shield-check"></i> ${proj.healthScore} / 100 (${proj.healthStatus})
        </span>
      </td>
      <td>
        <div class="d-flex align-items-center gap-1">
          <span class="text-danger fw-bold">${proj.issuesCount.critical}</span> Critical · 
          <span class="text-warning fw-bold">${proj.issuesCount.high}</span> High
        </div>
      </td>
      <td class="text-secondary">${proj.lastAnalyzed}</td>
      <td class="text-end">
        <div class="dropdown">
          <button class="btn btn-icon btn-secondary-custom btn-sm" type="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-three-dots-vertical"></i>
          </button>
          <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end">
            <li><a class="dropdown-menu-item text-white px-3 py-1 d-block" href="project-details.html?id=${proj.id}"><i class="bi bi-eye me-2"></i>View Details</a></li>
            <li><a class="dropdown-menu-item text-white px-3 py-1 d-block" href="analysis.html?id=${proj.id}"><i class="bi bi-play-circle me-2"></i>Run Analysis</a></li>
            <li><hr class="dropdown-divider border-secondary"></li>
            <li><a class="dropdown-menu-item text-danger px-3 py-1 d-block" href="#" onclick="handleDeleteProject('${proj.id}', event)"><i class="bi bi-trash me-2"></i>Delete</a></li>
          </ul>
        </div>
      </td>
    </tr>
  `).join('');
}

function handleDeleteProject(id, event) {
  event.preventDefault();
  if (confirm("Are you sure you want to delete this project?")) {
    ApiService.deleteProject(id).then(() => {
      showToast("Project Deleted", "Project was removed successfully", "success");
      ApiService.getProjects().then(renderProjectsTable);
    });
  }
}
