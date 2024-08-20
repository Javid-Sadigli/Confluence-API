package com.example.confluence_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.confluence_api.model.ConfluenceUserEntity;

public interface ConfluenceUserRepository extends JpaRepository<ConfluenceUserEntity, String> {}