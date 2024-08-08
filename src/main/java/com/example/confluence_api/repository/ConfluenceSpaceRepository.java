package com.example.confluence_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.confluence_api.entity.ConfluenceSpaceEntity;

public interface ConfluenceSpaceRepository extends JpaRepository<ConfluenceSpaceEntity, String> {}