# 1. Record architecture decisions

Date: 2026-04-25

## Status

Accepted

## Context

Sketchware Pro started life as a community-maintained mod of the original
Sketchware app. The project has grown to ~200k LOC across Java + Kotlin with
many overlapping subsystems (legacy `a.a.a.*`, contributor `mod.*`, modern
`pro.sketchware.*`). Future contributors need a low-friction way to understand
why the codebase looks the way it does and to propose changes that affect
multiple modules.

## Decision

We will record significant architectural decisions in this directory using
the [ADR template](https://github.com/joelparkerhenderson/architecture-decision-record).
Each ADR is an immutable, numbered Markdown file describing the context, the
decision, and the consequences. Superseded ADRs are kept for history and link
to the ADR that replaces them.

## Consequences

* New contributors can answer "why is X done this way?" by reading ADRs.
* Architectural debates leave a paper trail instead of being scattered across
  Discord/Telegram/PR comments.
* PRs that materially change architecture must add or update an ADR.
