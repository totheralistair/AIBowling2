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
3. **Code** — write code and tests. Provide the exact terminal command to run:
   - Normal slice: `cd ~/Desktop/AI_Games/Bowlingv2/AppForXxx && mvn test`
   - `connect hexagons` slice: see Maven Wiring section below.
4. **Tests pass locally** — then commit and push. Claude provides the git commit command; user runs `git push` from their terminal (Claude has no network access).
5. **GitHub Actions** — check the Actions tab. Report errors back.

## Folder Structure (per app)

Language-specific structure to be confirmed at project setup. General pattern:

```
AppForXxx/
  BizLogic/
    XxxService              ← core logic, no adapter dependencies
    Tests/
      XxxServiceTest        ← test case is always the primary actor
  Ports/
    Incoming/
      ForConfiguring/       ← interface: greetings(), connectXxx()
      ForUsing/             ← interface: business methods
    Outgoing/
      ForXxx/               ← interface: one per secondary actor, named forXxx
  Adapters/
    IncomingAdapters/
      ForConfiguring/       ← implements ForConfiguring interface
      ForUsing/             ← implements ForUsing interface
    OutgoingAdapters/
      ForXxx/               ← MockYyy + real adapter (added later)
```

## Step 0 — Create Folder Structure (before any code)

**Before writing any code for a new app, create the full folder skeleton.** All folders are created empty (use `.gitkeep` to commit empty folders). This declares the architecture before any implementation exists.

Folders to create for every new app:
```
AppForXxx/
  BizLogic/
  Ports/                ← typed languages only (Java, C#, Go, etc.)
    Incoming/           ← not needed in JavaScript, Ruby, Python
      ForConfiguring/
      ForUsing/
    Outgoing/           ← add ForXxx/ subfolders as secondary actors are identified
  Adapters/
    IncomingAdapters/
      ForConfiguring/
      ForUsing/
    OutgoingAdapters/   ← add ForXxx/ subfolders as secondary actors are identified
  Tests/
```

**Note on Ports/:** Typed languages (Java, C#, Go) need the `Ports/` folder because interfaces must be declared explicitly as separate files. Dynamically typed languages (JavaScript, Ruby, Python) do not need `Ports/` — the port contract is implicit in the method names.

No code files are created in Step 0. Only folders and `.gitkeep` files.

## Key Rules

- Mocks live in `OutgoingAdapters/`, not in test folders.
- The test case is always the primary actor.
- Real adapters replace mocks without touching BizLogic.
- Do one slice at a time. Stop after each slice, run tests, get approval.
- Never write slices speculatively. Each slice comes from dialog.
- Slice 1 (greetings) has no constructor arguments.
- External systems that don't implement `greetings()` (e.g. payment gateway, hardware) need a case-by-case initial probe call specified in the slice.

## Maven Wiring for `connect hexagons` slices (Java)

When a `connect hexagons` slice replaces a mock with a real app, the calling app needs to reference the real app as a Maven dependency. Four things change:

**1. pom.xml of the calling app** — add the dependency:
```xml
<dependency>
    <groupId>bowling</groupId>
    <artifactId>AppForXxx</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

**2. Real adapter** — create `RealXxx.java` in the calling app's `Adapters/OutgoingAdapters/ForXxx/`. It implements the calling app's outgoing port interface and wraps the real `XxxService`:
```java
public class RealXxx implements ForXxx {
    private final XxxService xxxService;
    public RealXxx(XxxService xxxService) { this.xxxService = xxxService; }
    @Override
    public String greetings() { return xxxService.greetings(); }
}
```

**3. Terminal commands** — install the dependency app first, then test the calling app:
```
cd ~/Desktop/AI_Games/Bowlingv2/AppForXxx && mvn install
cd ~/Desktop/AI_Games/Bowlingv2/AppForCalling && mvn test
```

**4. CI (test.yml)** — add an install step for the dependency app before the test step in the calling app's job:
```yaml
- name: Install AppForXxx
  run: mvn install -DskipTests
  working-directory: AppForXxx
- name: Test AppForCalling
  run: mvn test
  working-directory: AppForCalling
```

## CI

Tests run automatically on every push via GitHub Actions. Each app runs independently in parallel.
