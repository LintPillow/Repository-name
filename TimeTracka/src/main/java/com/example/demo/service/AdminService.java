package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ArrivalEntity;
import com.example.demo.entity.DepartureEntity;
import com.example.demo.repository.ArrivalRepository;
import com.example.demo.repository.DepartureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ArrivalRepository arrivalRepository;
    private final DepartureRepository departureRepository;
    
    	
	    public List<ArrivalEntity> getAllArrivals() {
	    	System.out.println(arrivalRepository.findAll());
	        return arrivalRepository.findAll(); 
	    }
	    
	    // 退勤打刻 全件取得
	    public List<DepartureEntity> getAllDepartures() {
	        return departureRepository.findAll();
	    }
}
