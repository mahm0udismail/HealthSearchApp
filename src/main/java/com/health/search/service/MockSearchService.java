package com.health.search.service;

import com.health.search.exception.SearchException;
import com.health.search.model.SearchItem;
import com.health.search.model.SearchItem.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HashMap-based mock implementation of {@link SearchService}.
 *
 * <p>WHY HashMap? - Key = lowercased name → O(1) exact-match lookup - Value = SearchItem → instant
 * retrieval - For partial/fuzzy search we still stream values(), but the map gives us fast exact
 * lookups for free.
 */
public class MockSearchService implements SearchService {

  // Key = name in lowercase, Value = SearchItem
  private static final Map<String, SearchItem> DATA = new HashMap<>();

  static {
    addItem("Dr. Sarah Ahmed", "Cardiologist — Cairo Heart Center", Type.DOCTOR);
    addItem("Dr. Omar Hassan", "Orthopedic Surgeon — Nile Bone Clinic", Type.DOCTOR);
    addItem("Dr. Layla Mostafa", "Dermatologist — SkinCare Cairo", Type.DOCTOR);
    addItem("Dr. Karim Youssef", "Neurologist — Brain & Spine Institute", Type.DOCTOR);
    addItem("Dr. Nadia Farouk", "Pediatrician — Little Stars Clinic", Type.DOCTOR);
    addItem("Tele-Health", "Consult a doctor from home via video", Type.SERVICE);
    addItem("Lab Tests", "Book blood, urine & imaging tests", Type.SERVICE);
    addItem("Home Nursing", "Certified nurses sent to your address", Type.SERVICE);
    addItem("Pharmacy Delivery", "24/7 medicine delivery to your door", Type.SERVICE);
    addItem("Mental Health", "Therapy & counselling sessions online", Type.SERVICE);
    addItem("Physiotherapy", "Rehabilitation & recovery programs", Type.SERVICE);
    ;
  }

  private static void addItem(String name, String description, Type type) {
    DATA.put(name.toLowerCase(Locale.ROOT), new SearchItem(name, description, type));
  }

  @Override
  public List<SearchItem> search(String query) {
    if (query == null) {
      throw new SearchException("Search query must not be null");
    }

    String trimmed = query.trim();
    if (trimmed.isBlank()) {
      return List.of();
    }

    String lower = trimmed.toLowerCase(Locale.ROOT);

    // 1. Try exact match first — O(1)
    if (DATA.containsKey(lower)) {
      return List.of(DATA.get(lower));
    }

    // 2. Fall back to partial match across all values — O(n)
    return DATA.values().stream().filter(item -> matches(item, lower)).collect(Collectors.toList());
  }

  private boolean matches(SearchItem item, String lowerQuery) {
    return item.getName().toLowerCase(Locale.ROOT).contains(lowerQuery)
        || item.getDescription().toLowerCase(Locale.ROOT).contains(lowerQuery);
  }
}
