# System Responsibilities

## Main Use Case — Use and manage one bowling session

1. Each Bowler checks in using the **Checkin App**, which notifies the **Lanes Manager** of the person's name.
2. The Manager uses the **Lanes Manager** to assign a lane. The **Lanes Manager** notifies the **Lane Governor** to activate — no bowler name is sent.
3. The Bowler walks to the lane and types their name into the **Bowler At Lane** tablet. The **Bowler At Lane** notifies the **Lane Governor** that the bowler has arrived — no name is sent to the **Lane Governor**.
4. After each roll, the **Lane Governor** commands the pinsetter, tracks roll 1 or roll 2, and notifies the **Bowler At Lane** of the pin situation. The **Bowler At Lane** applies scoring rules and shows the bowler the current game state.
5. The Bowler ends the session. The **Bowler At Lane** notifies the **Lanes Manager** that the lane is free.
6. The Manager uses the **Lanes Manager** to collect payment via the **Payment Gateway**.

**Key design decisions:**
- **Lanes Manager** owns all lane state — which lanes are free, occupied, who is on which lane.
- **Lane Governor** never receives a bowler's name — no Need-To-Know.
- **Lane Governor** receives two distinct notifications: activate (from Lanes Manager), and bowlerArrived (from Bowler At Lane).
- Scoring rules live entirely in **Bowler At Lane** — pluggable per game type.
- **Bowler At Lane** notifies **Lanes Manager** directly when session ends, bypassing Lane Governor.

---

## Checkin App
**Job:** Let a bowler check in and register their name with the Lanes Manager.

**Not My Job:**
- Assigning lanes
- Knowing lane state
- Scoring
- Payment

---

## Lanes Manager
**Job:** Owns all lane state (free/occupied), assigns lanes to bowlers, manages Lane Governors, collects payment via Payment Gateway.

**Not My Job:**
- Collecting bowler personal details
- Scoring
- Knowing bowler names once handed off to a lane

---

## Lane Governor
**Job:** Governs the physical lane — activates/deactivates, commands the pinsetter (internal hardware, not a software port), tracks roll 1 or roll 2, reports pin situation to Bowler At Lane.

**Not My Job:**
- Knowing the bowler's name
- Scoring or game rules
- Payment
- Lane assignment

---

## Bowler At Lane
**Job:** Lets the bowler register at the lane, receives pin situation from Lane Governor, applies scoring rules (pluggable per game type), displays game state, notifies Lanes Manager when session ends.

**Not My Job:**
- Physical lane control
- Payment
- Lane assignment
- Knowing which lane it is running on
