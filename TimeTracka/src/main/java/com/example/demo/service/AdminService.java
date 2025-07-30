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
		return arrivalRepository.findAll();
	}

	// 退勤打刻 全件取得
	public List<DepartureEntity> getAllDepartures() {
		return departureRepository.findAll();
	}

//	    出勤打刻IDで削除したあと、全件取得
	public List<ArrivalEntity> deleteArrival(Long id) {
		arrivalRepository.deleteById(id);
		return arrivalRepository.findAll();
	}

//	    退勤打刻IDで削除した後、全件取得
	public List<DepartureEntity> deleteDepartures(Long id) {
		departureRepository.deleteById(id);
		return departureRepository.findAll();
	}
}