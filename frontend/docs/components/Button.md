# Button

File: `src/components/Button.jsx`

## Purpose

A reusable full-width button with loading support and consistent styling.

## Props

- `children`: button label or content
- `loading`: when true, disables the button and shows a spinner
- `className`: extra Tailwind classes
- any other native button props

## Behavior

- disables itself when `loading` is true
- shows a spinner while loading
- applies the app's shared rounded, colored button style
