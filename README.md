# course-registration-frontend

Frontend for the course registration system.

## Requirements

- Node.js and npm installed
- Backend server running on `http://localhost:8080`

## Clone the repository

HTTPS:

```bash
git clone https://csgit.ucalgary.ca/alan.yong/course-registration-frontend.git
```

SSH:

```bash
git clone git@csgit.ucalgary.ca:alan.yong/course-registration-frontend.git
```

## Install dependencies

From the project folder, install the React/Vite dependencies with npm:

```bash
npm i
```

## Run the app

Start the frontend in development mode:

```bash
npm run dev
```

Vite will print the local URL in the terminal. Open it in your browser to test the app.

## Backend setup

Make sure the backend is running before logging in. This frontend expects the API at `http://localhost:8080`.

## Login details

Student login:

- Email: `student@example.com`
- Password: `student123`

Admin login:

- Email: `admin@example.com`
- Password: `admin123`

Admin users should sign in from the admin login page:

- Open `/admin-login`
- Enter the admin email and password
- Continue to `/admin-dashboard` after login

## Login endpoint

The backend login request should use `POST /api/auth/login` with a JSON body like this:

```json
{
  "email": "student@example.com",
  "password": "student123"
}
```

Example admin request:

```json
{
  "email": "admin@example.com",
  "password": "admin123"
}
```

Note: the current frontend code is wired to `POST /api/auth/login` for both student and admin sign-in. Make sure the backend is running and exposes the route your build expects before testing.

## Available scripts

- `npm run dev` - start the development server
- `npm run build` - build the project for production
- `npm run lint` - run ESLint
- `npm run preview` - preview the production build

## Documentation

See the generated file-by-file docs in [docs/README.md](docs/README.md).
