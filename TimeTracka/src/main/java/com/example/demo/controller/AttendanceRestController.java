package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.AttendanceDAO;
import com.example.demo.entity.ArrivalEntity;
import com.example.demo.entity.DepartureEntity;
import com.example.demo.repository.ArrivalRepository;
import com.example.demo.repository.DepartureRepository;
import com.example.demo.service.NiceAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AttendanceRestController {

    private final ArrivalRepository arrivalRepository;
    private final DepartureRepository departureRepository;
    private final NiceAdminService niceAdminService;

    @GetMapping("/api/attendances")
    public List<AttendanceDAO> getAttendances() {
        return niceAdminService.getAllAttendances();
    }

    @PostMapping("/api/attendances/update")
    public String updateAttendance(@RequestBody AttendanceDAO dao) {
        LocalDate date = LocalDate.parse(dao.getDate());

        if (dao.getArrivalTime() != null && !dao.getArrivalTime().isEmpty()) {
            arrivalRepository.save(createOrUpdateArrival(dao.getUserName(), date, dao.getArrivalTime()));
        }

        if (dao.getDepartureTime() != null && !dao.getDepartureTime().isEmpty()) {
            departureRepository.save(createOrUpdateDeparture(dao.getUserName(), date, dao.getDepartureTime()));
        }

        return "success";
    }


    private ArrivalEntity createOrUpdateArrival(String userName, LocalDate date, String timeStr) {
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.parse(timeStr));
        return arrivalRepository.findByUserNameAndDate(userName, date.atStartOfDay(), date.atTime(LocalTime.MAX))
                .map(entity -> {
                    entity.setTimestamp(dateTime);
                    return entity;
                }).orElseGet(() -> {
                    ArrivalEntity entity = new ArrivalEntity();
                    entity.setUserName(userName);
                    entity.setTimestamp(dateTime);
                    return entity;
                });
    }

    private DepartureEntity createOrUpdateDeparture(String userName, LocalDate date, String timeStr) {
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.parse(timeStr));
        return departureRepository.findByUserNameAndDate(userName, date.atStartOfDay(), date.atTime(LocalTime.MAX))
                .map(entity -> {
                    entity.setTimestamp(dateTime);
                    return entity;
                }).orElseGet(() -> {
                    DepartureEntity entity = new DepartureEntity();
                    entity.setUserName(userName);
                    entity.setTimestamp(dateTime);
                    return entity;
                });
    }

    @DeleteMapping("/api/attendances")
    public void deleteAttendance(@RequestParam(required = false) Long arrivalId,
                                 @RequestParam(required = false) Long departureId) {
        if (arrivalId != null) {
            arrivalRepository.deleteById(arrivalId);
        }
        if (departureId != null) {
            departureRepository.deleteById(departureId);
        }
    }
}
