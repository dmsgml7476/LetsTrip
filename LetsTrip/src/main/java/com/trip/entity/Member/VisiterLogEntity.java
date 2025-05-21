package com.trip.entity.Member;

import lombok.*;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="visiter_log")
public class VisiterLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="visiter_log_id")
    private Long id;

    @Column(name = "visit_date", nullable = false, unique = true)
    private LocalDate visitDate;  

    @Column(name = "visit_count", nullable = false)
    private int visitCount; 
	
}
