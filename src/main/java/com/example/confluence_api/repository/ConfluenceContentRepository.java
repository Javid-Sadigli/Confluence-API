package com.example.confluence_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.confluence_api.model.ConfluenceContentEntity;

@Repository
public interface ConfluenceContentRepository extends JpaRepository<ConfluenceContentEntity, String> {}