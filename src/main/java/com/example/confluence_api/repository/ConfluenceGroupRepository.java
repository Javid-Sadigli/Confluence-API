package com.example.confluence_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.confluence_api.entity.GroupEntity;

@Repository
public interface ConfluenceGroupRepository extends JpaRepository<GroupEntity, String> {}