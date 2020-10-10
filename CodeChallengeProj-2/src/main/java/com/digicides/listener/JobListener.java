package com.digicides.listener;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.batch.runtime.BatchStatus;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {
	 private SXSSFWorkbook workBook;
	 private FileOutputStream oos;
	 
	public JobListener(SXSSFWorkbook workBook,FileOutputStream oos) {
		this.workBook=workBook;
		this.oos=oos;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		BatchStatus batchStatus=jobExecution.getStatus().getBatchStatus();
		if(batchStatus==BatchStatus.COMPLETED) {
			try {
				workBook.write(oos);
				oos.close();
				workBook.dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
