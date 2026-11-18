package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.MtRestaurant;
import com.example.demo.repository.MtRestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MtEditServuceImpl implements MtEditService {

	private final MtRestaurantRepository repository;
	
	@Override
	public void edit(MtRestaurant restaurant) {
		
		repository.update(restaurant);
		
	}

}