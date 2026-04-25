# 3. Defer the modular Gradle refactor; introduce package boundaries first

Date: 2026-04-25

## Status

Accepted

## Context

The professional roadmap proposes a multi-module Gradle structure
(`core-*`, `feature-*`, `builder-*`, `blocks-*`, `components-*`,
`plugins-*`). Splitting the existing monolithic `:app` module is a
multi-month effort because legacy Java code in `a.a.a.*`, `com.besome.*`
and `mod.agus.*` has dense cyclic dependencies. Doing this in one PR
would block all other work and produce a giant unreviewable diff.

## Decision

In Phase 1 we leave the Gradle module structure as-is and instead:

1. Establish package boundaries by isolating new code under
   `pro.sketchware.<feature>`.
2. Add Detekt + Spotless to enforce style on the new code only
   (legacy packages are excluded via `targetExclude` in `spotless`).
3. Author ADRs for any boundary-crossing change so the eventual
   modularisation has a clear migration map.

The actual `:core-common`, `:core-ui`, `:feature-projects` Gradle modules
will be introduced incrementally in Phase 2 once at least one feature has
a clean package boundary that can be lifted out.

## Consequences

* No "big bang" refactor PR. Reviewers can keep up.
* Modularisation slips into Phase 2 / Q2 2026.
* New code must follow the package convention or risk being absorbed back
  into the monolith.
