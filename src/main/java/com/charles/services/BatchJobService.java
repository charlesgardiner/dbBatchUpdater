/*
 * © Copyright 2015 -  Charles Gardiner
 */
package com.charles.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final static Logger LOGGER = LoggerFactory.getLogger(BatchJobService.class);
  
  // //////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // ////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Autowired
  private BatchJobRepository batchJobRepository;
  
  @Autowired
  private BatchJobBuilderService batchJobBuilderService;
  
  // ///////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  // //////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public BatchJob createBatchJob(CreateBatchJobDto createBatchJob, BatchJobType type) {
    
    BatchJob batchJob =  new BatchJob();
    BeanUtils.copyProperties(createBatchJob, batchJob);
    batchJob.setBatchJobType(type);
    batchJob = batchJobRepository.save(batchJob);
    
    // scan the database to build the batch job object
    batchJobBuilderService.buildBatchJob(batchJob);
    
    return batchJob;
    
  }
  
  // ------------------------ Implements:

  // ------------------------ Overrides:

  // ---------------------------- Abstract Methods -----------------------------

  // ---------------------------- Utility Methods ------------------------------

  // ---------------------------- Property Methods -----------------------------
}
