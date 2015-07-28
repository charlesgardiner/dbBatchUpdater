/*
 * © Copyright 2015 -  Charles Gardiner
 */

package com.charles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.charles.data.BatchJob;
import com.charles.data.BatchJobType;
import com.charles.data.dto.CreateBatchJobDto;
import com.charles.services.BatchJobService;

@RestController
public class BatchJobController {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @Autowired
  private BatchJobService batchJobService;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  @RequestMapping(value = "batchjob/changeJobTitle", method = RequestMethod.POST)
  public ResponseEntity<String> postBatchJobChangeJobTitle(@RequestBody CreateBatchJobDto createBatchJobDto) {
    BatchJob batchJob = batchJobService.createBatchJob(createBatchJobDto, BatchJobType.JOB_TITLE);
    return ResponseEntity.ok("Batch Job has been created.");
  }


  @RequestMapping(value = "batchjob/changeIndustry", method = RequestMethod.POST)
  public ResponseEntity<String>  postBatchJobChangeIndustry(@RequestBody CreateBatchJobDto createBatchJobDto) {
    BatchJob batchJob = batchJobService.createBatchJob(createBatchJobDto, BatchJobType.INDUSTRY);
    return ResponseEntity.ok("Batch Job has been created.");
  }

  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------
}
