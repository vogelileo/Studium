# Non-functional requirements

## Quality characteristics

## Functional Stability

- **Functional Completeness:**

  - The code must be able to run all features specified before

- **Functional Correctness:**
  - The code must be able to be compliant with relevant standards

<!-- - **Functional Appropriateness (de: Angemessenheit):** The degree to which the functions facilitate the accomplishment of specified tasks and objectives. -->

## Performance efficiency

- **Time behaviour:**

  - Needs to be in the time specified before. As fast as possible or at least as fast that the user has a good experience using the VIDEOSYS
  - DB queries < 0,5s
  - Website loading times < 0,5s

- **Resource utilization:**

  - MAX GB of Programm 1GB
  - MAX storage per Movie description 1MB
  - MAX RAM of Programm 200mb

- **Capacity:**
  - Assuming every customer is a new customer, and every customer rents out 6 DVDs a time, and rentals are processed at 3 parallel stations, the accumulated time per full rental transaction must not exceed 6 minutes. (Customer demand: The system must be able to process a up to 30 rentals per hour)

## Compatibility

- **Co-existence:**

  - Program does not include any other system to co-exist

- **Interoperability:**
  - Connection with EuroPay needs to be working. Protocol defined for it

## Usability

- **Appropriateness recognizability:**

  - The user interface must clearly communicate its purpose — renting, returning, and managing DVDs — through intuitive labels and layout.

- **Learnability:**

  - A new shop clerk must be able to perform a full rental transaction (including payment and return) after **1 minutes** of guided instruction.
  - Tooltips, hints, and input validation messages must guide users through workflows.

- **Operability:**

  - All frequent actions (rent, return, register, view availability) must be reachable within **three clicks** from the home screen.
  - Keyboard shortcuts and tab navigation must be supported for speed.

- **User error protection:**

  - The system must warn before deleting or overwriting any customer, rental, or title data.

- **User interface aesthetics:**

  - The design must follow a consistent color scheme (e.g., blue/grey) and clear typography.

- **Accessibility:**
  - The system must be operable using only a keyboard.
  - Text must meet **WCAG 2.1 AA** contrast ratios.
  - Language must be adjustable between **English** and **German** to support local clerks.
  - Screen readers must correctly read form fields and button labels.

---

## Reliability

- **Maturity:**

  - The system must function correctly for at least **1,000 rental transactions** without software failure.
  - Automated tests verify database integrity and payment communication.

- **Availability:**

  - If offline, local transactions must queue and sync automatically once connectivity is restored.

- **Fault tolerance:**

  - A temporary loss of connection to EuroPay or CashSystem must not crash VIDEOSYS; queued transactions are retried automatically.

- **Recoverability:**
  - In case of system failure, the application must restore to the last consistent state within **5 minutes** using database backups.
  - Backups are made automatically every **hour** and retained for **30 days**.

---

## Security

- **Confidentiality:**

  - Customer personal and payment data must be stored and transmitted using **AES-256 encryption**.

- **Integrity:**

  - Payment data must not be editable once confirmed by EuroPay or CashSystem.

- **Non-repudiation (de: Nicht-Abstreitbarkeit):**

  - Each transaction (rental, payment, return) must include a timestamp, clerk ID, and system signature to ensure traceability.

- **Accountability:**

  - Logs are stored securely for a minimum of **6 months**.

- **Authenticity:**
  - User login requires secure authentication (username + password).

---

## Maintainability

- **Modularity:**

  - System components (Customer, Rental, Payment, Inventory, Reporting) must be independent modules to allow isolated updates.
  - Each module communicates via defined interfaces.

- **Reusability:**

  - The core rental and payment logic should be reusable for both the **desktop** and **web** versions of VIDEOSYS.

- **Analysability:**

  - Unit test coverage must exceed **80 %**.

- **Modifiability:**

  - Configuration files must allow price adjustments without code modification.

- **Testability:**
  - Each component must include automated tests verifying its inputs, outputs, and error handling.
  - Integration tests must simulate full rental cycles (rent → return → billing).

---

## Portability

- **Adaptability:**

  - The system must run on **Windows, macOS, and Linux**.
  - It should support both **local database** (SQLite) and **remote database** (PostgreSQL) deployments.

- **Installability:**

  - Installation must complete in under **5 minutes** with minimal dependencies.
  - The setup wizard should auto-configure database connections and payment endpoints.
  - Updates can be applied via an in-app update mechanism without reinstallation.

- **Replaceability:**
  - VIDEOSYS must be capable of replacing an existing manual or legacy rental system within **one business day** of transition.
  - All historical rental data must be importable from CSV or existing databases.
