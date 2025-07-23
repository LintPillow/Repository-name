package com.example.demo.service;

//import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.example.demo.dao.AttendanceDAO;
import com.example.demo.entity.ArrivalEntity;
import com.example.demo.entity.DepartureEntity;
import com.example.demo.repository.ArrivalRepository;
import com.example.demo.repository.DepartureRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NiceAdminService {

	private final ArrivalRepository arrivalRepository;
	private final DepartureRepository departureRepository;

	// 勤怠全件取得（一覧表示用）
	public List<AttendanceDAO> getAllAttendances() {
		List<ArrivalEntity> arrivals = arrivalRepository.findAll();
		List<DepartureEntity> departures = departureRepository.findAll();

		// key: ユーザー名_日付 → DepartureEntity
		Map<String, DepartureEntity> departureMap = new HashMap<>();
		for (DepartureEntity d : departures) {
			String key = d.getUserName() + "_" + d.getTimestamp().toLocalDate();
			departureMap.put(key, d);
		}

		List<AttendanceDAO> result = new ArrayList<>();
		for (ArrivalEntity a : arrivals) {
			String dateStr = a.getTimestamp().toLocalDate().toString();
			String key = a.getUserName() + "_" + dateStr;
			LocalDateTime arrivalTime = a.getTimestamp();
			DepartureEntity d = departureMap.get(key);
			LocalDateTime departureTime = (d != null) ? d.getTimestamp() : null;

			AttendanceDAO dao = new AttendanceDAO();
			dao.setArrivalId(a.getId());
			dao.setDepartureId((d != null) ? d.getId() : null);
			dao.setUserName(a.getUserName());
			dao.setDate(dateStr);
			dao.setArrivalTime(arrivalTime.toLocalTime().toString());
			dao.setDepartureTime(departureTime != null ? departureTime.toLocalTime().toString() : "-");

			if (departureTime != null) {
				long minutes = java.time.Duration.between(arrivalTime, departureTime).toMinutes();
				long h = minutes / 60;
				long m = minutes % 60;
				dao.setWorkTime(h + "時間" + m + "分");
			} else {
				dao.setWorkTime("-");
			}

			result.add(dao);
		}

		return result;
	}

	// ID指定で削除
	@Transactional
	public void deleteAttendanceById(Long arrivalId, Long departureId) {
		if (arrivalId != null) {
			arrivalRepository.deleteById(arrivalId);
		}
		if (departureId != null) {
			departureRepository.deleteById(departureId);
		}
	}
}
