package com.digicides.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.batch.runtime.BatchStatus;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.digicides.entity.Farmer;
import com.digicides.repository.FarmerRepo;

public class JobListener implements JobExecutionListener {
	 private SXSSFWorkbook workBook;
	 private FileOutputStream oos;
	 @Autowired
	 private FarmerRepo repo;
	 
	public JobListener(SXSSFWorkbook workBook,FileOutputStream oos) {
		this.workBook=workBook;
		this.oos=oos;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		Farmer farmer1=new Farmer(3232l, "sbp", 2323232233l, "4:00pm");
		Farmer farmer2=new Farmer(32332l, "sbp1", 2333223232233l, "4:00pm");
		Farmer farmer3=new Farmer(32322l, "sb2p", 2323232233l, "4:00pm");
		repo.saveAll(Arrays.asList(farmer1,farmer2,farmer3));
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		BatchStatus batchStatus=jobExecution.getStatus().getBatchStatus();
		if(batchStatus==BatchStatus.COMPLETED) {
			try {
				workBook.write(new FileOutputStream("farmer.xlsx"));
				
				workBook.dispose();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
