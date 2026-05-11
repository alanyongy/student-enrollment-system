# Admin Entity Page

File: `src/pages/AdminEntityPage.jsx`

## Purpose

Provides the generic CRUD interface used by the admin sections under `/admin/:entity`.

## Route parameter

- `entity`: selects the config entry from `adminEntities`

## Core behavior

- loads rows from the configured admin endpoint
- loads relation dropdown options for columns marked as relations
- supports create, edit, and delete
- supports pagination, sorting, and page navigation
- maps relation fields to nested backend objects when saving

## Dependencies

- `adminEntities` for schema and endpoints
- `api` for network requests
- `Button` and `Input` for form controls

## Notes

If the route name does not exist in `adminEntities`, the page shows an unknown entity message.
