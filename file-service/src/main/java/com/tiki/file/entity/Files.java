package com.tiki.file.entity;

import com.tiki.file.dto.FileType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("files")
public class Files {

    @MongoId
    private String id;
    private String url;
    private boolean status = false;
    private String duration;
    private FileType type;
}