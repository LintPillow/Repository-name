package com.example.demo.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ArrivalEntity;
import com.example.demo.entity.DepartureEntity;
import com.example.demo.repository.ArrivalRepository;
import com.example.demo.repository.DepartureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditService {
	
	private final ArrivalRepository arrivalRepository;
	private final DepartureRepository departureRepository;
	

	
//	idを検索
	public Optional<ArrivalEntity> findArrival(String userName, LocalDate date) {
	    return arrivalRepository.findByUserNameAndDate(userName, date);
	}

	public Optional<DepartureEntity> findDeparture(String userName, LocalDate date) {
	    return departureRepository.findByUserNameAndDate(userName, date);
	}
	
//	更新処理
	public void saveArrival(ArrivalEntity entity) {
	    arrivalRepository.save(entity);
	}

	public void saveDeparture(DepartureEntity entity) {
	    departureRepository.save(entity);
	}

}
