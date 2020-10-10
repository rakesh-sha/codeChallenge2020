package com.digicides.queryProvider;

import javax.persistence.Query;

import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.digicides.entity.Farmer;

import lombok.Setter;


/**
 * Class to Generate database query dynamically 
 *
 * @author rakesh
 *
 */
@Setter
public class JpaQueryProviderImpl extends AbstractJpaQueryProvider{
	
	private Class<Farmer> entity;
	
	private String query;

	/**
	 * For creating Query
	 */
	@Override
	public Query createQuery() {
		return getEntityManager().createNamedQuery(query,entity);
	}

	/**
	 * for validation of states
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(StringUtils.hasText(query), "Query cannot be empty");
	    Assert.notNull(entity, "Entity class cannot be NULL");
		
	}
	

}
