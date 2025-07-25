package com.example.demo.form;

import lombok.Data;

@Data
public class EditForm {
    private String name;
    private String date; // 例：2025-07-25（時間無し）
    private String arrivalTime; // 例：09:00
    private String departureTime; // 例：17:00
}