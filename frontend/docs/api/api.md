# API Layer

File: `src/api/api.js`

## Purpose

Provides a shared Axios client and login helper functions for the backend at `http://localhost:8080`.

## Exports

### `api`

Axios instance with:

- `baseURL` set to `http://localhost:8080`
- a request interceptor that attaches `Authorization: Bearer <token>` from `localStorage`

### `loginStudent(email, password)`

Sends a `POST` request to:

```text
/api/student/login
```

Behavior:

- sends `{ email, password }`
- throws on non-OK responses
- stores `data.token` in `localStorage`
- returns the parsed backend response

### `loginAdmin(email, password)`

Sends a `POST` request to:

```text
/api/admin/login
```

Behavior:

- sends `{ email, password }`
- returns the parsed backend JSON

## Notes

The login pages in `src/pages/Login.jsx` and `src/pages/AdminLogin.jsx` currently post to `POST /api/auth/login` and expect a token inside the response message, so the backend contract should match the page you are using.
