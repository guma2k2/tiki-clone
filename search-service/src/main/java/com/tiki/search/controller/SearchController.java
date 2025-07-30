package com.tiki.search.controller;

import com.tiki.search.dto.ApiResponse;
import com.tiki.search.dto.request.SearchRequest;
import com.tiki.search.dto.request.SuggestionRequest;
import com.tiki.search.dto.response.SearchResponse;
import com.tiki.search.service.SearchService;
import com.tiki.search.service.SuggestionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchController {

    SearchService searchService;

    SuggestionService suggestionService;


    @GetMapping("/suggestions")
    public ApiResponse<List<String>> suggest(@RequestBody SuggestionRequest parameters){
        return ApiResponse.<List<String>>builder().result(suggestionService.fetchSuggestions(parameters)).code(1200).build();
    }

    @GetMapping("/search")
    public ApiResponse<SearchResponse> search(SearchRequest parameters){
        return ApiResponse.<SearchResponse>builder().result(searchService.search(parameters)).code(1200).build();
    }

}