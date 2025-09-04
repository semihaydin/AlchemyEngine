# Non-Functional Testing Strategy

## 1. Problem Diagnosis

The manual team has been exhibiting performance problems on the dashboard, especially on mobile. My first steps will be:

- **Reproduce & Observe:** Use browser DevTools (Network & Performance tabs) and mobile simulating to replicate the issue.
- **Baseline Measurements:**
- **Page Load Time** (First Contentful Paint, Largest Contentful Paint).
    - **Time to Interactive (TTI).**
    - **API Response Times** (average, p95, p99).
    - **Render/Frame Rate** during dashboard interactions.
    - **Memory & CPU usage** on simulated mid-tier mobile devices.
- **Measure Impact:** Capture metrics across 3–5 runs on desktop and mobile, then compare with industry standards (e.g., TTI < 5s on 3G/4G mobile).

---

## 2. Tooling & Methodology (Backend Performance)

From my toolkit (JMeter, Postman):

- **API Load Testing using JMeter**
    - Simulate concurrent users making requests to dashboard APIs.
- Critical situations: login, dashboard load, data refresh, filters applied.
    - Performance metrics: response time distribution, error rate, throughput.
    - Model workload: start with 50–100 VUs ramp-up, gradually increase until breaking point reached.

- **Postman (Collection Runner + Newman)**
    - Test correctness of API under load.
- Add light smoke performance tests (reaction < X ms).

- **Load Simulation**
    - Ramp-up schedule (e.g., +10 users every 30s).
    - Use think times to simulate pacing in real life.
    - Include spike testing (sudden 2–3x user load).

---

## 3. Frontend Performance (Component-Dense UI like Nivo)

- **Browser Tools:**
- Chrome DevTools Lighthouse for audits (performance score, LCP, TTI, CLS).
    - Record CPU & memory while interacting with charts.

- **Mobile Simulation:**
    - Utilize Chrome's throttling (CPU slow down, 3G/4G network).
    - Test mid-range devices (e.g., Moto G, iPhone 11 equivalent).

- **Benchmarks & Alerts:**
    - Establish baselines for:
- LCP < 2.5s
- TTI < 5s
- FPS ≥ 50 on chart updates
- Establish "performance budgets" – if breached, flag in CI.

---

## 4. Integration into QA Process

- **When to Run:**
    - **Daily lightweight runs**: Postman sanity checks on API latency.
- **JMeter nightly runs**: Load tests against staging with reports stored away.
    - **Pre-release Lighthouse audits**: Keep an eye out for no regressions in performance scores.

- **How to Integrate:**
    - Include JMeter & Lighthouse reports in CI pipeline (GitHub Actions / Jenkins).
    - Retain reports as build artifacts to be visible.
- Fail only upon high-level regressions (e.g., TTI +50%).

- **Process Ownership:**
    - QA owns setup & maintain scripts.
    - Devs and QA review reports together prior to release.
    - Issues tracked as performance bugs in the backlog.

---

## 5. Continuous Improvement

- Trend analysis between builds to identify long-term regressions.
- Expand coverage: stress tests, soak tests.
- Introduce correlation with user feedback: correlation of test reports with bug reports.
- Perform quarterly "performance review" to reset baselines as features mature.

---