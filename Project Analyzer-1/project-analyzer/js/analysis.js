/**
 * Project Analyzer — Real-time Analysis Simulator
 * Animates a 5-step static analysis engine scan with console logs and progress updates.
 */

document.addEventListener('DOMContentLoaded', () => {
  if (document.getElementById('analysisProgressContainer')) {
    startFakeAnalysis();
  }
});

function startFakeAnalysis() {
  const steps = [
    { text: "Scanning source files & AST parsing...", percent: 20, log: "[INFO] Discovered 148 Java files, 24 YAML configs, 12 HTML templates." },
    { text: "Analyzing project dependencies & vulnerability databases...", percent: 40, log: "[WARN] Detected 3 CVE vulnerability matches in Jackson Databind." },
    { text: "Checking code security & OWASP Top 10 rules...", percent: 60, log: "[ALERT] Hardcoded password pattern matched in UserService.java line 42." },
    { text: "Calculating architecture coupling, cohesion & debt metrics...", percent: 80, log: "[INFO] Layered Architecture validation passed (Coupling Index: 91/100)." },
    { text: "Finalizing overall project health report...", percent: 100, log: "[SUCCESS] Project health score computed: 84 / 100." }
  ];

  let currentStep = 0;
  const progressBar = document.getElementById('analysisProgressBar');
  const percentText = document.getElementById('analysisPercentText');
  const stepText = document.getElementById('analysisStepText');
  const consoleBox = document.getElementById('analysisConsoleBox');
  const completionBox = document.getElementById('analysisCompletionBox');
  const spinner = document.getElementById('analysisSpinner');

  function runNextStep() {
    if (currentStep < steps.length) {
      const step = steps[currentStep];

      if (progressBar) progressBar.style.width = `${step.percent}%`;
      if (percentText) percentText.textContent = `${step.percent}%`;
      if (stepText) stepText.textContent = step.text;
      
      if (consoleBox) {
        const time = new Date().toLocaleTimeString();
        consoleBox.innerHTML += `<div class="mb-1 text-secondary"><span class="text-muted">[${time}]</span> ${step.log}</div>`;
        consoleBox.scrollTop = consoleBox.scrollHeight;
      }

      currentStep++;
      setTimeout(runNextStep, 900);
    } else {
      // Analysis Complete!
      if (spinner) spinner.style.display = 'none';
      if (completionBox) completionBox.classList.remove('d-none');
      if (stepText) stepText.textContent = "Analysis Complete!";
      showToast("Analysis Complete", "Project Analyzer has finished scanning your codebase.", "success");
    }
  }

  setTimeout(runNextStep, 500);
}
