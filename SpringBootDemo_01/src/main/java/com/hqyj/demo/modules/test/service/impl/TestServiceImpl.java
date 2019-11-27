package com.hqyj.demo.modules.test.service.impl;

import com.hqyj.demo.modules.test.service.TestService;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hqyj.demo.modules.test.dao.TestDao;
import com.hqyj.demo.modules.test.entity.City;
import com.hqyj.demo.modules.test.entity.Country;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	
	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		return Optional.ofNullable(testDao.getCitiesByCountryId(countryId))
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

}
