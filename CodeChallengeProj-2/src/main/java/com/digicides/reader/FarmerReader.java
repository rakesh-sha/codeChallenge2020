package com.digicides.reader;

import java.util.Iterator;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.digicides.entity.Farmer;
import com.digicides.repository.FarmerRepo;

public class FarmerReader implements ItemReader<Farmer> {

	    @Autowired
	    private FarmerRepo respository;

	    private Iterator<Farmer> usersIterator;

	    @BeforeStep
	    public void before(StepExecution stepExecution) {
	        usersIterator = respository.findAll().iterator();
	    }

	    @Override
	    public Farmer read() {
	        if (usersIterator != null && usersIterator.hasNext()) {
	            return usersIterator.next();
	        } else {
	            return null;
	        }
	    }
	}

