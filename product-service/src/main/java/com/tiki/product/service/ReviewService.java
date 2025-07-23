package com.tiki.product.service;

import com.tiki.product.dto.PageResponse;
import com.tiki.product.dto.request.ReviewCreateRequest;
import com.tiki.product.dto.response.CustomerResponse;
import com.tiki.product.dto.response.ReviewResponse;
import com.tiki.product.entity.Attribute;
import com.tiki.product.entity.ProductVariant;
import com.tiki.product.entity.Review;
import com.tiki.product.entity.VariantAttributeValue;
import com.tiki.product.repository.ProductVariantRepository;
import com.tiki.product.repository.ReviewRepository;
import com.tiki.product.repository.VariantAttributeValueRepository;
import com.tiki.product.repository.client.CustomerFeignClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewService {

    ReviewRepository reviewRepository;

    ProductVariantRepository productVariantRepository;

    CustomerFeignClient customerFeignClient;

    VariantAttributeValueRepository variantAttributeValueRepository;

    void createReview(ReviewCreateRequest reviewCreateRequest) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        ProductVariant productVariant = productVariantRepository.findById(reviewCreateRequest.productVariantId()).orElseThrow();
        Review review = Review.builder()
                .content(reviewCreateRequest.content())
                .ratingStar(reviewCreateRequest.ratingStar())
                .productVariant(productVariant)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build();
        reviewRepository.save(review);
    }


    public PageResponse<ReviewResponse> getReviewsByProductId(
            Long productId,
            int page,
            int size,
            List<Integer> ratingStars
    ) {

        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Review> reviewPage = null;

        if (ratingStars != null && !ratingStars.isEmpty()) {
            reviewPage = reviewRepository.findByProductIdAndRatingStar(productId, pageable, ratingStars);
        } else {
            reviewPage = reviewRepository.findByProductId(productId, pageable);
        }

        List<Review> reviews = reviewPage.getContent();
        int totalPages = reviewPage.getTotalPages();
        long totalElements = reviewPage.getTotalElements();

        List<ReviewResponse> reviewResponses = reviews.stream().map(review -> {
            String customerId = review.getCustomerId();
            Long productVariantId = review.getProductVariant().getId();
            String variant = getVariantValuesByProductId(productVariantId);
            CustomerResponse customerResponse = customerFeignClient.getCustomerById(customerId).getResult();
            return ReviewResponse.fromModel(review, customerResponse, variant);
        }).collect(Collectors.toList());


        return PageResponse.<ReviewResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .data(reviewResponses)
                .build();

    }

    private String getVariantValuesByProductId(Long id) {
        StringBuffer result = new StringBuffer();
        List<VariantAttributeValue> variantAttributeValues = variantAttributeValueRepository.findByProductVariant(id);
        for (int i = 0; i < variantAttributeValues.size(); i++) {
            if (i > 0) {
                result.append(" . ");
            }
            VariantAttributeValue variantAttributeValue = variantAttributeValues.get(i);
            Attribute attribute = variantAttributeValue.getAttribute();
            result.append((attribute.getName()).concat(" : ")).append(variantAttributeValue.getValue());
        }
        return result.toString();
    }

}
