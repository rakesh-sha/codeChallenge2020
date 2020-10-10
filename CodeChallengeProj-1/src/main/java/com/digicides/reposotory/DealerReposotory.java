package com.digicides.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.digicides.entity.Dealer;

/**
 * interface to deal with all persistence operation 
 * 
 * @author rakes
 *
 */
@Component
public interface DealerReposotory extends JpaRepository<Dealer, Integer> {

}
