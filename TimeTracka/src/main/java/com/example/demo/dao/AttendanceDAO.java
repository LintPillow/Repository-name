package com.example.demo.dao;

import lombok.Data;

@Data
public class AttendanceDAO {
	private Long arrivalId;        // 出勤ID（削除用）
	private Long departureId;      // 退勤ID（削除用）

	private String userName; 	   // ユーザ名
	private String date; 		   // yyyy-MM-dd の形式（日付）
	private String arrivalTime;    // 出勤時刻 (hh:mm)
	private String departureTime;  // 退勤時刻 (hh:mm)
	private String workTime; 	   // 勤務時間（表示専用）
}
