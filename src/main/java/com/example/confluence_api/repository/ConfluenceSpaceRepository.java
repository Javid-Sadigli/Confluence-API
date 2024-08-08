package com.example.confluence_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.confluence_api.entity.SpaceEntity;

public interface ConfluenceSpaceRepository extends JpaRepository<SpaceEntity, String> {}