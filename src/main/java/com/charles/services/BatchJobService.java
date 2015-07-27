/*
 * © Copyright 2015 -  Charles Gardiner
 */
package com.charles.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.dto.CreateBatchJobDto;
import com.charles.data.mongo.BatchJobRepository;

@Service
public class BatchJobService {

  // /////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Autowired
  private BatchJobRepository batchJobRepository;
  
  // ///////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // //////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public BatchJob createBatchJob(CreateBatchJobDto createBatchJob, BatchJobType type) {
    
    BatchJob batchJob =  new BatchJob();
    BeanUtils.copyProperties(createBatchJob, batchJob);
    batchJob.setBatchJobType(type);
    return batchJobRepository.save(batchJob);
  }
  
  // ------------------------ Implements:

  // ------------------------ Overrides:

  // ---------------------------- Abstract Methods -----------------------------

  // ---------------------------- Utility Methods ------------------------------

  // ---------------------------- Property Methods -----------------------------
}
