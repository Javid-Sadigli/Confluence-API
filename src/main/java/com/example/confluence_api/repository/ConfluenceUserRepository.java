package com.example.confluence_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.confluence_api.entity.UserEntity;

public interface ConfluenceUserRepository extends JpaRepository<UserEntity, String> {}