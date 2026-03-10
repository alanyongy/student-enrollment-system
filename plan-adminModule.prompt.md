## Plan: Admin Module for Course Registration System

Build a secure, role-based RESTful Admin module for the Course Registration System. Admins authenticate via JWT, access a protected dashboard, and manage students, instructors, courses, and sections. All admin actions are performed via RESTful endpoints secured by JWT and ROLE_ADMIN.

### Steps
1. Design authentication flow: login page, POST /api/auth/login, JWT issuance, frontend token storage.
2. Implement backend security: JWT validation, role-based access (ROLE_ADMIN), protect /api/admin/** endpoints.
3. Develop Admin Dashboard: display summary stats (students, instructors, courses, sections), restrict access to admins.
4. Create RESTful endpoints for student management: create, view, update, delete, enroll/drop sections.
5. Create RESTful endpoints for instructor management: create, update, delete, assign/remove sections.
6. Create RESTful endpoints for course and section management: create, update, delete courses and sections; handle relationships.
7. Ensure proper REST conventions for all endpoints (POST, GET, PUT, PATCH, DELETE).

### Further Considerations
1. Should admin account creation be manual or seeded for initial setup?
2. Confirm frontend framework and JWT storage method (localStorage, sessionStorage, etc.).
3. Plan for student self-registration in future; currently only admin/IT can create accounts.

