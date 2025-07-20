package com.tiki.file.dto.response;

import com.tiki.file.entity.Files;

public record FileResponse(
        String id,
        String url,
        String type,
        boolean status,
        String duration
) {
    public static FileResponse fromModel(Files media) {
        return new FileResponse(media.getId(), media.getUrl(), media.getType().toString(), media.isStatus() ,media.getDuration());
    }
}
