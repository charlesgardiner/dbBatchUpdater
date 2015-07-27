/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles.data.mongo;

import java.util.List;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BatchJobRepository extends MongoRepository<BatchJob, String> {

////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  List<BatchJob> findByFromValueAndBatchJobType(String fromValue, BatchJobType type);
  
}