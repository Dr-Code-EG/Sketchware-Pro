# Translating Sketchware Pro

Sketchware Pro ships an English UI with progressively-extended translations
for the largest user communities. This document explains how to contribute
translations.

## Where strings live

* Source-of-truth English strings: `app/src/main/res/values/strings.xml`.
* Per-locale overrides: `app/src/main/res/values-<locale>/strings.xml`.
  When a key is missing in a locale file Android falls back to the base
  English string, so partial translations are always safe to merge.

## Supported locales

Declared in `app/src/main/res/xml/locales_config.xml`. We only list a
locale here once at least a partial translation exists at
`app/src/main/res/values-<locale>/strings.xml` — otherwise the
Android 13+ system language picker would advertise a language that
silently falls back to English.

### Currently shipping
| Tag    | Language          | Status        |
|--------|-------------------|---------------|
| `en`   | English (default) | 100%          |
| `ar`   | Arabic            | Phase 1 seed  |

### Planned (will be added to `locales_config.xml` together with their `values-<locale>/`)
- `id` — Indonesian
- `pt-BR` — Portuguese (Brazil)
- `es` — Spanish
- `ru` — Russian
- `hi` — Hindi
- `fa` — Persian
- `tr` — Turkish
- `vi` — Vietnamese

## How to translate

1. Find your locale folder in `app/src/main/res/values-<locale>/`. If it
   doesn't exist yet, create it and copy `strings.xml` from `values/`.
2. Translate strings in place. Keep the `name` attribute identical; only
   change the value. Preserve any format placeholders (`%s`, `%d`,
   `%1$d`, etc.) and HTML tags exactly as in the source.
3. Open a PR. Title format: `i18n(<locale>): translate <area>`.

## Per-app language at runtime

The app uses AndroidX AppCompat 1.7's per-app language API
(`AppCompatDelegate.setApplicationLocales`). The user's choice is
persisted automatically via the `AppLocalesMetadataHolderService`
declared in `AndroidManifest.xml` with `autoStoreLocales=true`. There is
no need to wrap activities in a custom `LocaleHelper` for context.

## RTL languages

Arabic and Persian are right-to-left. The app already declares
`android:supportsRtl="true"` (default true on AppCompat themes) and most
layouts use `start`/`end` instead of `left`/`right`. If you spot a layout
that mirrors incorrectly, please open an issue with a screenshot.
