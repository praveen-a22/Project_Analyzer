/**
 * Project Analyzer — Main UI Controller
 * Manages layout, sidebar active state, toast notifications, search, and global interactions.
 */

document.addEventListener('DOMContentLoaded', () => {
  initActiveSidebarLink();
  initMobileOffcanvas();
  initTooltips();
});

// Highlight current page link in sidebar based on current URL path
function initActiveSidebarLink() {
  const currentPath = window.location.pathname;
  const pageName = currentPath.split('/').pop() || 'index.html';

  const sidebarLinks = document.querySelectorAll('.sidebar-link');
  sidebarLinks.forEach(link => {
    const href = link.getAttribute('href');
    if (href && (href.endsWith(pageName) || (pageName === '' && href.includes('dashboard.html')))) {
      link.classList.add('active');
    } else {
      link.classList.remove('active');
    }
  });
}

// Mobile sidebar toggle handler
function initMobileOffcanvas() {
  const mobileBtn = document.getElementById('mobileNavToggle');
  const sidebar = document.querySelector('.app-sidebar');

  if (mobileBtn && sidebar) {
    mobileBtn.addEventListener('click', () => {
      sidebar.classList.toggle('show-sidebar');
    });

    // Close when clicking outside on mobile
    document.addEventListener('click', (e) => {
      if (!sidebar.contains(e.target) && !mobileBtn.contains(e.target) && sidebar.classList.contains('show-sidebar')) {
        sidebar.classList.remove('show-sidebar');
      }
    });
  }
}

// Bootstrap Tooltips initialization
function initTooltips() {
  if (typeof bootstrap !== 'undefined' && bootstrap.Tooltip) {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new bootstrap.Tooltip(tooltipTriggerEl);
    });
  }
}

// Custom Toast Notification System
function showToast(title, message, type = 'info') {
  let toastContainer = document.getElementById('toastContainer');
  if (!toastContainer) {
    toastContainer = document.createElement('div');
    toastContainer.id = 'toastContainer';
    toastContainer.className = 'toast-container-custom position-fixed bottom-0 end-0 p-3';
    toastContainer.style.zIndex = '1100';
    document.body.appendChild(toastContainer);
  }

  const iconMap = {
    success: 'bi-check-circle-fill text-success',
    danger: 'bi-exclamation-triangle-fill text-danger',
    warning: 'bi-exclamation-circle-fill text-warning',
    info: 'bi-info-circle-fill text-info'
  };

  const borderMap = {
    success: 'border-success',
    danger: 'border-danger',
    warning: 'border-warning',
    info: 'border-info'
  };

  const toastId = `toast-${Date.now()}`;
  const toastHtml = `
    <div id="${toastId}" class="toast align-items-center text-white bg-card-theme border ${borderMap[type] || 'border-secondary'} shadow-lg mb-2" role="alert" aria-live="assertive" aria-atomic="true">
      <div class="d-flex">
        <div class="toast-body d-flex align-items-center gap-2">
          <i class="bi ${iconMap[type] || iconMap.info} fs-5"></i>
          <div>
            <strong class="d-block text-white" style="font-size:0.875rem;">${title}</strong>
            <span class="text-secondary" style="font-size:0.8rem;">${message}</span>
          </div>
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
      </div>
    </div>
  `;

  toastContainer.insertAdjacentHTML('beforeend', toastHtml);
  const toastEl = document.getElementById(toastId);
  if (typeof bootstrap !== 'undefined' && bootstrap.Toast) {
    const bsToast = new bootstrap.Toast(toastEl, { delay: 4000 });
    bsToast.show();
    toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
  }
}

// Animate numbers up counter helper
function animateCounter(elementId, targetValue, duration = 1200) {
  const el = document.getElementById(elementId);
  if (!el) return;

  const start = 0;
  const startTime = performance.now();

  function update(currentTime) {
    const elapsed = currentTime - startTime;
    const progress = Math.min(elapsed / duration, 1);
    const easeOutVal = Math.floor(start + (targetValue - start) * (1 - Math.pow(1 - progress, 3)));
    
    el.textContent = easeOutVal;

    if (progress < 1) {
      requestAnimationFrame(update);
    } else {
      el.textContent = targetValue;
    }
  }

  requestAnimationFrame(update);
}
