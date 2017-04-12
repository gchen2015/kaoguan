package com.kaoguan.app.domain;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A Upload.
 */
@Document(collection = "UPLOAD")
public class Upload implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8623921569188303707L;

    @Id
    private String id;

    @Field("original_name")
    private String originalName;


    @Field("uploaded_at")
    private LocalDate uploadedAt;

    @Field("md5sum")
    private String md5sum;

    @Field("upload_complete")
    private Boolean uploadComplete;

    @Field("total_chunks")
    private Integer totalChunks;

    @Field("total_size")
    private Long totalSize;


    @Field("completed_at")
    private LocalDate completedAt;

    /*
     * Manually added because JHipster doesn't generate this type.
     */
    private List<Boolean> chunks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public LocalDate getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDate uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public Boolean getUploadComplete() {
        return uploadComplete;
    }

    public void setUploadComplete(Boolean uploadComplete) {
        this.uploadComplete = uploadComplete;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
    }

    public List<Boolean> getChunks() {
        return chunks;
    }

    public void setChunks(List<Boolean> chunks) {
        this.chunks = chunks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Upload upload = (Upload) o;

        if (!Objects.equals(id, upload.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Upload{" + "id=" + id + ", originalName='" + originalName + "'"
            + ", uploadedAt='" + uploadedAt + "'" + ", md5sum='" + md5sum
            + "'" + ", uploadComplete='" + uploadComplete + "'"
            + ", totalChunks='" + totalChunks + "'" + ", totalSize='"
            + totalSize + "'" + ", completedAt='" + completedAt + "'" + '}';
    }
}
