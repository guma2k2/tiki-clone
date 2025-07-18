package com.tiki.product.ultility;

import java.text.Normalizer;

public class SlugGenerator {
    public static String toSlug(String input) {
        if (input == null || input.isBlank()) return "";

        // Bước 1: Bỏ dấu tiếng Việt
        String noAccent = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        // Bước 2: Loại bỏ ký tự đặc biệt
        String clean = noAccent.replaceAll("[^a-zA-Z0-9\\s]", "");

        // Bước 3: Chuyển sang thường và thay khoảng trắng
        return clean.toLowerCase().trim().replaceAll("\\s+", "-");
    }
}
