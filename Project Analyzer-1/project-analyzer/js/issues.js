/**
 * Project Analyzer — Code Issues Inspector Logic
 */

document.addEventListener('DOMContentLoaded', () => {
  if (document.getElementById('issuesTbody')) {
    initIssuesPage();
  }
});

let allIssuesData = [];

function initIssuesPage() {
  ApiService.getIssues().then(issues => {
    allIssuesData = issues;
    renderIssuesTable(issues);
  });

  document.getElementById('issueSearchInput')?.addEventListener('input', filterIssues);
  document.getElementById('severityFilterSelect')?.addEventListener('change', filterIssues);
  document.getElementById('categoryFilterSelect')?.addEventListener('change', filterIssues);
}

function filterIssues() {
  const search = (document.getElementById('issueSearchInput')?.value || '').toLowerCase();
  const severity = document.getElementById('severityFilterSelect')?.value || 'all';
  const category = document.getElementById('categoryFilterSelect')?.value || 'all';

  const filtered = allIssuesData.filter(iss => {
    const matchesSearch = iss.title.toLowerCase().includes(search) || iss.file.toLowerCase().includes(search) || iss.description.toLowerCase().includes(search);
    const matchesSeverity = severity === 'all' || iss.severity.toLowerCase() === severity.toLowerCase();
    const matchesCategory = category === 'all' || iss.category.toLowerCase() === category.toLowerCase();

    return matchesSearch && matchesSeverity && matchesCategory;
  });

  renderIssuesTable(filtered);
}

function renderIssuesTable(issues) {
  const tbody = document.getElementById('issuesTbody');
  if (!tbody) return;

  if (issues.length === 0) {
    tbody.innerHTML = `
      <tr>
        <td colspan="7" class="text-center py-4 text-muted">No issues match the selected filter criteria.</td>
      </tr>
    `;
    return;
  }

  const badgeMap = {
    "Critical": "badge-critical",
    "High": "badge-high",
    "Medium": "badge-medium",
    "Low": "badge-low"
  };

  tbody.innerHTML = issues.map(iss => `
    <tr>
      <td>
        <span class="badge-custom ${badgeMap[iss.severity] || 'badge-low'}">${iss.severity}</span>
      </td>
      <td>
        <span class="fw-bold text-white">${iss.title}</span>
        <div class="text-muted small">${iss.description.substring(0, 60)}...</div>
      </td>
      <td><span class="tech-pill">${iss.category}</span></td>
      <td><code class="text-info">${iss.file}</code></td>
      <td><span class="badge bg-secondary-theme text-white">L${iss.line}</span></td>
      <td>
        <span class="badge ${iss.status === 'Open' ? 'bg-danger' : 'bg-warning'} bg-opacity-25 text-white">${iss.status}</span>
      </td>
      <td class="text-end">
        <button class="btn btn-secondary-custom btn-sm" onclick="openIssueModal('${iss.id}')">
          <i class="bi bi-code-slash me-1"></i> Inspect
        </button>
      </td>
    </tr>
  `).join('');
}

function openIssueModal(issueId) {
  const issue = allIssuesData.find(i => i.id === issueId);
  if (!issue) return;

  document.getElementById('issueModalTitle').textContent = issue.title;
  document.getElementById('issueModalSeverity').className = `badge-custom ${issue.severity === 'Critical' ? 'badge-critical' : issue.severity === 'High' ? 'badge-high' : 'badge-medium'}`;
  document.getElementById('issueModalSeverity').textContent = issue.severity;
  document.getElementById('issueModalCategory').textContent = issue.category;
  document.getElementById('issueModalFile').textContent = `${issue.file} (Line ${issue.line})`;
  document.getElementById('issueModalDescription').textContent = issue.description;
  document.getElementById('issueModalRecommendation').textContent = issue.recommendation;
  document.getElementById('issueModalCodeSnippet').textContent = issue.snippet;

  const modalEl = document.getElementById('issueDetailsModal');
  if (modalEl && typeof bootstrap !== 'undefined') {
    const modal = new bootstrap.Modal(modalEl);
    modal.show();
  }
}
