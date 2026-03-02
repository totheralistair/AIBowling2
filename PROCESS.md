# Development Process

## Guiding Principles

- **Hexagonal Architecture (Ports & Adapters):** BizLogic knows nothing about adapters. Adapters know about BizLogic.
- **"Not My Job":** Each app enforces tight responsibility boundaries. See RESPONSIBILITIES.md.
- **"Need-To-Know":** No object receives data it doesn't need.
- **Naming conventions:**
  - Methods named after the result, not the action: `cost()` not `getCost()`
  - Method names use active voice: `pinSituation(pins)` not `receivePinSituation(pins)` — verbs like `receive`, `handle`, `process` are passive
  - Test cases use long descriptive names: `test_it_returns_a_greeting`
  - Slice descriptions always use active voice

## Two Input Ports (every app)

Every app has at least two input ports and at least one output port:

- **forConfiguring** — used in development, testing, and system startup. Contains:
  - `greetings()` — proves the app is alive; returns `"greetings from <AppName>"`
  - `connectXxx(secondary)` — stores the secondary actor, immediately calls `secondary.greetings()`, and returns the result. Calling it again overwrites the stored actor (allows hot-swap).
- **forUsing** — the business port, named with the appropriate verb for that app (e.g. `forCheckingIn`, `forManagingLanes`). Contains all business methods.

The mock secondary replies `"greetings from mock Xxx"`. The real app replies `"greetings from Xxx"`. This distinguishes them at a glance.

## Slice Format (SLICES.xlsx)

One row per slice. Five columns:

| slice | app | purpose | call | response | 2ndary |
|---|---|---|---|---|---|

**purpose** is fixed vocabulary:
- `left of hexagon` — establishes the forConfiguring port and greetings()
- `right of hexagon` — establishes connectXxx() and the first output port
- `connect hexagons` — replaces a mock with a real app connection
- `business function` — adds a method to the forUsing port

Slices are numbered globally across all apps, not per-app.

## Working Sequence (every slice)

1. **Dialog** — discuss the next slice. Agree on what will be created.
2. **SLICES.xlsx** — add the new row. Review before writing any code.
3. **Code** — write code and tests. Say: "Run tests in AppForXxx."
4. **Tests pass locally** — then push to git.
5. **GitHub Actions** — check the Actions tab. Report errors back.

## Folder Structure (per app)

Language-specific structure to be confirmed at project setup. General pattern:

```
AppForXxx/
  BizLogic/
    XxxService         ← core logic, no adapter dependencies
    Tests/
      XxxServiceTest   ← test case is always the primary actor
  Adapters/
    IncomingAdapters/
      ForConfiguring/  ← greetings(), connectXxx() — interface + implementation
      ForUsing/        ← business methods — interface + implementation
    OutgoingAdapters/
      ToYyy/           ← one folder per secondary actor
        YyyPort        ← interface (in typed languages)
        MockYyy        ← mock implementation
```

## Key Rules

- Mocks live in `OutgoingAdapters/`, not in test folders.
- The test case is always the primary actor.
- Real adapters replace mocks without touching BizLogic.
- Do one slice at a time. Stop after each slice, run tests, get approval.
- Never write slices speculatively. Each slice comes from dialog.
- Slice 1 (greetings) has no constructor arguments.
- External systems that don't implement `greetings()` (e.g. payment gateway, hardware) need a case-by-case initial probe call specified in the slice.

## CI

Tests run automatically on every push via GitHub Actions. Each app runs independently in parallel.
