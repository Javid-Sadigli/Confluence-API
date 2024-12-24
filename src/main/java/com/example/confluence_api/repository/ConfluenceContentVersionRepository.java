package com.example.confluence_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.confluence_api.model.ConfluenceContentVersionEntity;

public interface ConfluenceContentVersionRepository extends JpaRepository<ConfluenceContentVersionEntity, Long> 
{
    @Query(
        value = "SELECT * FROM confluence_content_versions v WHERE v.content_id = :content_id AND v.number = :version_number",
        nativeQuery = true
    )
    Optional<ConfluenceContentVersionEntity> findByContentIdAndVersionNumber(
        @Param("content_id") String contentId,
        @Param("version_number") int versionNumber
    );
}
