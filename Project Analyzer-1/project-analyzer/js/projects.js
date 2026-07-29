/**
 * Project Analyzer — Projects List & Filtering Page
 */

document.addEventListener('DOMContentLoaded', () => {
  if (document.getElementById('projectsContainer')) {
    initProjectsPage();
  }
});

let allProjectsData = [];

function initProjectsPage() {
  ApiService.getProjects().then(projects => {
    allProjectsData = projects;
    renderProjectsCards(projects);
  });

  const searchInput = document.getElementById('projectSearchInput');
  if (searchInput) {
    searchInput.addEventListener('input', filterProjects);
  }

  const filterTabs = document.querySelectorAll('.project-filter-tab');
  filterTabs.forEach(tab => {
    tab.addEventListener('click', (e) => {
      filterTabs.forEach(t => t.classList.remove('active', 'btn-primary-custom'));
      filterTabs.forEach(t => t.classList.add('btn-secondary-custom'));
      e.target.classList.remove('btn-secondary-custom');
      e.target.classList.add('active', 'btn-primary-custom');

      filterProjects();
    });
  });
}

function filterProjects() {
  const searchTerm = (document.getElementById('projectSearchInput')?.value || '').toLowerCase();
  const activeTab = document.querySelector('.project-filter-tab.active')?.getAttribute('data-filter') || 'all';

  const filtered = allProjectsData.filter(proj => {
    const matchesSearch = proj.name.toLowerCase().includes(searchTerm) || proj.description.toLowerCase().includes(searchTerm) || proj.technology.toLowerCase().includes(searchTerm);
    
    let matchesStatus = true;
    if (activeTab === 'healthy') matchesStatus = proj.healthStatus === 'Healthy' || proj.healthStatus === 'Excellent' || proj.healthStatus === 'Good';
    else if (activeTab === 'warning') matchesStatus = proj.healthStatus === 'Warning';
    else if (activeTab === 'critical') matchesStatus = proj.healthStatus === 'Critical';

    return matchesSearch && matchesStatus;
  });

  renderProjectsCards(filtered);
}

function renderProjectsCards(projects) {
  const container = document.getElementById('projectsContainer');
  if (!container) return;

  if (projects.length === 0) {
    container.innerHTML = `
      <div class="col-12 text-center py-5 text-secondary">
        <i class="bi bi-folder-x fs-1 text-muted d-block mb-2"></i>
        <h5>No projects found</h5>
        <p class="small">Try adjusting your search criteria or create a new project.</p>
      </div>
    `;
    return;
  }

  const badgeMap = {
    "Excellent": "badge-excellent",
    "Healthy": "badge-healthy",
    "Good": "badge-good",
    "Warning": "badge-warning",
    "Critical": "badge-critical"
  };

  container.innerHTML = projects.map(proj => `
    <div class="col-lg-6 col-xl-6 mb-4">
      <div class="card-custom h-100 p-4">
        <div class="d-flex align-items-start justify-content-between mb-3">
          <div class="d-flex align-items-center gap-3">
            <div class="brand-icon" style="width:44px; height:44px; font-size:1.4rem;">
              <i class="bi bi-box-seam"></i>
            </div>
            <div>
              <h5 class="fw-bold mb-0 text-white">${proj.name}</h5>
              <span class="text-muted small">${proj.technology}</span>
            </div>
          </div>
          <span class="badge-custom ${badgeMap[proj.healthStatus] || 'badge-good'}">
            Score: ${proj.healthScore}/100
          </span>
        </div>

        <p class="text-secondary small mb-3">${proj.description}</p>

        <div class="d-flex flex-wrap gap-1 mb-4">
          ${proj.techStack.map(tech => `<span class="tech-pill">${tech}</span>`).join('')}
        </div>

        <div class="row g-2 text-center bg-secondary-theme p-3 rounded-3 mb-4">
          <div class="col-4 border-end border-secondary">
            <span class="d-block text-muted small">Issues</span>
            <span class="fw-bold text-white fs-6">${proj.issuesCount.critical + proj.issuesCount.high + proj.issuesCount.medium + proj.issuesCount.low}</span>
          </div>
          <div class="col-4 border-end border-secondary">
            <span class="d-block text-muted small">Coverage</span>
            <span class="fw-bold text-success fs-6">${proj.testCoverage}%</span>
          </div>
          <div class="col-4">
            <span class="d-block text-muted small">Dependencies</span>
            <span class="fw-bold text-info fs-6">${proj.dependenciesCount.total}</span>
          </div>
        </div>

        <div class="d-flex align-items-center justify-content-between pt-2 border-top border-secondary">
          <span class="text-muted small"><i class="bi bi-clock me-1"></i>${proj.lastAnalyzed}</span>
          <div class="d-flex gap-2">
            <a href="project-details.html?id=${proj.id}" class="btn btn-secondary-custom btn-sm"><i class="bi bi-eye"></i> View</a>
            <a href="analysis.html?id=${proj.id}" class="btn btn-primary-custom btn-sm"><i class="bi bi-play-fill"></i> Analyze</a>
          </div>
        </div>
      </div>
    </div>
  `).join('');
}
