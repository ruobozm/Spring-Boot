package com.hqyj.demo.modules.test.service.impl;

import com.hqyj.demo.modules.test.service.TestService;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.demo.modules.test.dao.TestDao;
import com.hqyj.demo.modules.test.entity.City;
import com.hqyj.demo.modules.test.entity.Country;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	
	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		return Optional.ofNullable(testDao.getCitiesByCountryId2(countryId))
				.orElse(Collections.emptyList());
	}

	@Override
	public Country getcountryByCountryId(int countryId) {
		return testDao.getcountryByCountryId(countryId);
	}

	@Override
	public Country getCountryByCountryName(String countryName) {
		return testDao.getCountryByCountryName(countryName);
	}
	
	/*
	* 分页查询
	*/
	@Override
	public PageInfo<City> getCitiesByPage(int currentPage, int pageSize) {
	PageHelper.startPage(currentPage, pageSize);
	List<City> cities = Optional.ofNullable(testDao.getCitiesByPage())
			.orElse(Collections.emptyList());
	return new PageInfo<>(cities);
	}
	
	@Override
	public City insertCity(City city) {
		testDao.insertCity(city);
		return city;
	}

	/* 
	 * 添加事务，并设置其属性。添加事务以后，出现运行时异常和error后自动回滚。noRollback在哪种异常时不回滚。
	 *  REQUIRED：支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。 
	 * Propagation：

	 SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行。 
	
	 MANDATORY：支持当前事务，如果当前没有事务，就抛出异常。 
	
	 REQUIRES_NEW：新建事务，如果当前存在事务，把当前事务挂起。 
	
	 NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。 
	
	 NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。 
	
	 NESTED：支持当前事务，如果当前事务存在，则执行一个嵌套事务，如果当前没有事务，就新建一个事务。 

	 */
	@Override
	@Transactional(noRollbackFor=ArithmeticException.class,propagation=Propagation.REQUIRED)
	public City updateCity(City city) {
		testDao.updateCity(city);
//		int i = 1 / 0;
		return city;
	}

	@Override
	public void deleteCity(int cityId) {
		testDao.deleteCity(cityId);
	}


}
