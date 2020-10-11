package com.digicides.config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.digicides.entity.Farmer;
import com.digicides.listener.JobListener;
import com.digicides.queryProvider.JpaQueryProviderImpl;
import com.digicides.reader.FarmerReader;
import com.digicides.repository.FarmerRepo;
import com.digicides.writer.FarmerWriter;

/**
 * Class to provide Cofiguration for spring batch
 *
 * @author rakesh
 *
 */
@Configuration
@EnableBatchProcessing
//@EnableJpaRepositories("com.digicides.repository")
public class BatchConfig {

	/**
	 * property to get JobBuilderFactory
	 * 
	 */
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	/**
	 * property to get StepBuilderFactory
	 * 
	 */
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	/**
	 * property to get datasource
	 */
	@Autowired
	private DataSource datasource;

	/**
	 * property to get {@link EntityManagerFactory}
	 */
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	/*
	 * @Autowired private FarmerRepo repo;
	 */

	private final Integer CHUNK = 1000;

	
	/* @Bean public JpaPagingItemReader<Farmer> reader() throws Exception{
	 JpaPagingItemReader<Farmer> databaseReader=new JpaPagingItemReader<Farmer>();
	 databaseReader.setEntityManagerFactory(entityManagerFactory);
	 JpaQueryProviderImpl queryProvider=new JpaQueryProviderImpl() ;
	 queryProvider.setQuery("Farmer.findAll");
	 queryProvider.setEntity(Farmer.class);
	 databaseReader.setQueryProvider(queryProvider);
	 databaseReader.setPageSize(1000); 
	 
	 databaseReader.afterPropertiesSet();
	 return  databaseReader;
	 
	 }*/
	 

	@Bean
	public ItemReader<Farmer> reader(FarmerRepo farmerRepo) {
	
		System.out.println(farmerRepo);
		RepositoryItemReader<Farmer> reader = new RepositoryItemReader<>();
		reader.setRepository(farmerRepo);
		reader.setMethodName("findAll");
		reader.setPageSize(CHUNK);
		reader.setSort(Collections.singletonMap("territory", Sort.Direction.ASC));
		System.out.println(reader.toString());
		return reader;
	
	}

	@Bean
	public SXSSFWorkbook workBook() {
		return new SXSSFWorkbook(CHUNK);
	}

	@Bean
	public ItemWriter<Farmer> writer(SXSSFWorkbook workBook) {
		SXSSFSheet sheet = workBook.createSheet("Farmer");
		System.out.println("BatchConfig.writer()");
		return new FarmerWriter(sheet);

	}

	@Bean
	public Step step(ItemReader<Farmer> reader, ItemWriter<Farmer> writer) {
		return stepBuilderFactory.get("step")
				.<Farmer, Farmer>chunk(CHUNK)
				.reader(reader)
				.writer(writer).build();
	}

	@Bean
	public JobExecutionListener listener() throws FileNotFoundException {
		return new JobListener(workBook(), new FileOutputStream("farmerInfo.xlsx"));
	}

	@Bean
	public Job job(Step step, JobExecutionListener listener) {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.listener(listener)
				.build();
	}

}
