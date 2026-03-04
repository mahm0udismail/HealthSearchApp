# HealthConnect — Search Bar
### Java Developer Assessment | COB Solution

A JavaFX desktop application that allows users to search for doctors
and medical services by name or description.

---

## Features

- Search doctors and services by name or description
- Press **Enter** or click **Search** to submit
- **Exact match** returns instantly — O(1) via HashMap
- **Partial match** scans all items — O(n) fallback
- Case-insensitive search
- Input validation with user-friendly error messages
- Clear button resets the search
- Clean, modern UI with CSS styling

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Core language |
| JavaFX | 21.0.6 | Desktop UI framework |
| FXML | 21.0.6 | Declarative UI layout |
| Maven | 3.9+ | Build & dependency management |
| JUnit 5 | 5.12.1 | Unit testing |
| Google Java Format | 2.23 | Code formatting |

---

## Project Structure

```
HealthSearchApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── module-info.java
│   │   │   └── com/health/search/
│   │   │       ├── HealthApp.java                 ← Entry point
│   │   │       ├── controller/
│   │   │       │   └── HomeController.java        ← UI event handling
│   │   │       ├── model/
│   │   │       │   └── SearchItem.java            ← Data object
│   │   │       ├── service/
│   │   │       │   ├── SearchService.java         ← Interface
│   │   │       │   └── MockSearchService.java     ← HashMap implementation
│   │   │       ├── exception/
│   │   │       │   └── SearchException.java       ← Domain exception
│   │   │       └── util/
│   │   │           └── InputValidator.java        ← Validation rules
│   │   └── resources/com/health/search/
│   │       ├── HomeView.fxml                      ← UI layout
│   │       └── styles.css                         ← Styling
│   └── test/
│       └── java/com/health/search/
│           └── SearchTest.java                    ← Unit tests
├── ARCHITECTURE.md                                ← Design decisions
├── CODE_WALKTHROUGH.md                            ← Line by line explanation
└── pom.xml                                        ← Maven config
```

---

## Architecture

Follows **MVC + Service Layer** pattern:

```
View (FXML)  →  Controller  →  SearchService (interface)
                                     └── MockSearchService (HashMap)
```

- **View** — FXML + CSS, zero business logic
- **Controller** — handles events, validates input, updates UI
- **Service** — business logic, fully swappable via interface
- **Model** — immutable data object

---

## Data Structure

Uses **HashMap** for O(1) exact match lookup:

```java
Map<String, SearchItem> DATA = new HashMap<>();
// Key   = name lowercased  →  "tele-health"
// Value = SearchItem object

// Step 1: exact match  — O(1)
if (DATA.containsKey(query)) return List.of(DATA.get(query));

// Step 2: partial match — O(n) fallback
return DATA.values().stream()
           .filter(item -> item.getName().contains(query))
           .collect(Collectors.toList());
```

---

## Exception Handling

```
Layer 1 — InputValidator    → blank or too long input
Layer 2 — SearchException   → domain-specific errors
Layer 3 — catch Exception   → unexpected errors (never crashes UI)
```

---

## How to Run

**Prerequisites:** Java 21+

```bash
# Clone
git clone https://github.com/mahm0udismail/HealthSearchApp.git
cd HealthSearchApp

# Run
./mvnw javafx:run

# Run tests
./mvnw test

# Format code
./mvnw fmt:format
```

---

## Search Examples

| Query | Result |
|---|---|
| `dr` | All 5 doctors |
| `tele` | Tele-Health service |
| `cairo` | Matches items with "Cairo" in description |
| `Tele-Health` | Exact match — instant O(1) lookup |
| *(empty)* | Validation error message |
| `xyz` | "No results found" message |

---

## Mock Data (11 items)

| Type | Name | Specialty |
|---|---|---|
| DOCTOR | Dr. Sarah Ahmed | Cardiologist |
| DOCTOR | Dr. Omar Hassan | Orthopedic Surgeon |
| DOCTOR | Dr. Layla Mostafa | Dermatologist |
| DOCTOR | Dr. Karim Youssef | Neurologist |
| DOCTOR | Dr. Nadia Farouk | Pediatrician |
| SERVICE | Tele-Health | Video consultation |
| SERVICE | Lab Tests | Blood & imaging tests |
| SERVICE | Home Nursing | Nurse home visits |
| SERVICE | Pharmacy Delivery | 24/7 medicine delivery |
| SERVICE | Mental Health | Therapy & counselling |
| SERVICE | Physiotherapy | Rehabilitation |

---
