package com.trip.service.Member;

import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

	private final JavaMailSender mailSender;

    private static final String AUTH_CODE_KEY = "authCode";
    private static final String AUTH_EMAIL_KEY = "authEmail";
    private static final String AUTH_SUCCESS_KEY = "authSuccess";

    // 인증번호 생성 및 이메일 전송
    public boolean sendAuthCode(String email, HttpSession session) {
        try {
            String code = generateCode();

            // 세션에 저장
            session.setAttribute(AUTH_CODE_KEY, code);
            session.setAttribute(AUTH_EMAIL_KEY, email);
            session.setAttribute(AUTH_SUCCESS_KEY, false);

            // 이메일 전송
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Let's Trip 인증번호 안내");
            message.setText("인증번호는 [" + code + "] 입니다.");
            mailSender.send(message);

            System.out.println("[DEBUG] 이메일 전송 완료: " + email + ", 코드: " + code);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 인증번호 검증
    public boolean verifyCode(String inputCode, HttpSession session) {
        String savedCode = (String) session.getAttribute(AUTH_CODE_KEY);
        boolean success = inputCode != null && inputCode.equals(savedCode);
        session.setAttribute(AUTH_SUCCESS_KEY, success);
        return success;
    }

    // 인증 성공 여부 확인
    public boolean isVerified(HttpSession session) {
        Boolean verified = (Boolean) session.getAttribute(AUTH_SUCCESS_KEY);
        return verified != null && verified;
    }

    // 인증된 이메일 가져오기
    public String getAuthenticatedEmail(HttpSession session) {
        return (String) session.getAttribute(AUTH_EMAIL_KEY);
    }

    private String generateCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // 6자리 숫자
    }
}
