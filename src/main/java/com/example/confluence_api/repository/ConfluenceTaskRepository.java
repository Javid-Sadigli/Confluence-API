package com.example.confluence_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.confluence_api.model.ConfluenceTaskEntity;

public interface ConfluenceTaskRepository extends JpaRepository<ConfluenceTaskEntity, String> {}