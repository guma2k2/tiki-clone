package com.tiki.product.ultility;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SkuGenerator {


    public static String generateSku(String productName, Map<String, String> attributes) {
        String base = slugify(productName).toUpperCase();

        // Lấy các thuộc tính có thứ tự cố định (ví dụ: Màu, Size)
        List<String> orderedAttributes = Arrays.asList("Màu sắc", "Size", "Chất liệu");
        List<String> attrValues = new ArrayList<>();

        for (String key : orderedAttributes) {
            if (attributes.containsKey(key)) {
                attrValues.add(slugify(attributes.get(key)).toUpperCase());
            }
        }

        // Nối thành SKU
        return base + "-" + String.join("-", attrValues);
    }

    // Hàm đơn giản hóa tiếng Việt và loại bỏ ký tự đặc biệt
    private static String slugify(String input) {
        if (input == null) return "";

        // Bỏ dấu tiếng Việt
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll("\\s+", "-");

        return normalized;
    }
}
