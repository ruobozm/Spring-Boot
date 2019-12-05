package com.hqyj.demo.modules.test.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hqyj.demo.modules.test.entity.City;
import com.hqyj.demo.modules.test.entity.Country;

public interface TestService {

	/**
	 * 根据国家id查询所有城市
	 */
	List<City> getCitiesByCountryId(int countryId);
	
	/**
	 * 根据国家id查询国家信息
	 */
	Country getcountryByCountryId(int countryId);
	
	/**
	 * 根据country name 查询国家信息
	 */
	Country getCountryByCountryName(String countryName);
	
	/**
	 * 分页查询城市信息
	 */
	PageInfo<City> getCitiesByPage(int currentPage, int pageSize);
	
	/**
	 * 插入城市
	 */
	City insertCity(City city);
	
	/**
	 * 更改城市信息
	 */
	City updateCity(City city);
	
	/**
	 * 删除城市
	 */
	void deleteCity(int cityId);
}
