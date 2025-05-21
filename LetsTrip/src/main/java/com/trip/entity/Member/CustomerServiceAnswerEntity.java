package com.trip.entity.Member;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="customer_service_answer")
public class CustomerServiceAnswerEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_service_answer_id")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="customer_service_id", nullable=false)
	private CustomerServiceEntity customerService;
	
	@Column(name="answer_text", nullable=false)
	private String answerText;
	
	@Column(name="answer_time", nullable=false)
	private LocalDateTime answerTime;
	
}
