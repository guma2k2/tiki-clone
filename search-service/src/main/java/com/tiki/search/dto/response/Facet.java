package com.tiki.search.dto.response;

import java.util.List;

public record Facet(String name,
                    List<FacetItem> items) {
}
