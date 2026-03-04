package com.health.search.service;

import com.health.search.model.SearchItem;
import java.util.List;

public interface SearchService {
  List<SearchItem> search(String query);
}
