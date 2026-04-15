package com.ecommerce.trendyoldemo.service;



import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.entity.VerificationTokenEntity;
import com.ecommerce.trendyoldemo.exception.CustomException;
import com.ecommerce.trendyoldemo.repository.UserRepository;
import com.ecommerce.trendyoldemo.repository.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    public String generateVerificationToken(UserEntity user) {
        SecureRandom random = new SecureRandom();

        // 100000 – 999999 arası random rəqəm (6 rəqəmli)
        int code = 100000 + random.nextInt(900000);
        String token = String.valueOf(code);

        VerificationTokenEntity verificationToken = VerificationTokenEntity.builder()
                .token(token)
                .user(user)
                .expirationDate(LocalDateTime.now().plusMinutes(30)) // 30 dəqiqəlik etibarlı
                .build();

        tokenRepository.save(verificationToken);

        return token;
    }


    public void sendVerificationEmail(String email, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Email Təsdiqi - BinaCopy");

        String content = "<p>Salam!</p>"
                + "<p>Sizin təsdiq kodunuz:</p>"
                + "<h2>" + token + "</h2>"
                + "<p>Bu kod 30 dəqiqə ərzində keçərlidir.</p>"
                + "<br><p>Əgər siz qeydiyyatdan keçməmisinizsə, bu mesajı nəzərə almayın.</p>";

        helper.setText(content, true);
        helper.setFrom("your-email@gmail.com");

        mailSender.send(message);
    }



    public void verifyAccount(String email, String token) {
        VerificationTokenEntity verificationToken = tokenRepository
                .findByUser_EmailAndToken(email, token)
                .orElseThrow(() -> new CustomException("Token və ya email səhvdir",
                        "Invalid token or email", "Invalid", 400, null));

        if (verificationToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CustomException("Tokenin vaxtı bitdi", "Token expired", "Expired", 400, null);
        }

        UserEntity user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken);
    }
}

