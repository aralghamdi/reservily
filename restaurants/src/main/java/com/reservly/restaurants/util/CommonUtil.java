package com.reservly.restaurants.util;

import com.reservly.restaurants.model.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {

    public static String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static <T> PageResponse<T> buildPageResponse(Page<T> page) {
        return PageResponse.<T>builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .items(page.getContent())
                .build();
    }
}
