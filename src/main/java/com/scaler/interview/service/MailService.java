package com.scaler.interview.service;

import com.scaler.interview.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    /*
     * The Spring Framework provides an easy abstraction for sending email by using
     * the JavaMailSender interface, and Spring Boot provides auto-configuration for
     * it as well as a starter module.
     */
    private JavaMailSender javaMailSender;

    /**
     * @param javaMailSender
     */
    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Meeting meeting, boolean update) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(meeting.getParticipantOne(), meeting.getParticipantTwo());
        if (update) {
            mail.setSubject(meeting.getAgenda());
            mail.setText("Meeting scheduled updated to " + meeting.getStartTime() + " to " + meeting.getEndTime() + " with " + meeting.getParticipantOne() + "  and " + meeting.getParticipantTwo());

        } else {
            mail.setSubject(meeting.getAgenda());
            mail.setText("Meeting scheduled at " + meeting.getStartTime() + " to " + meeting.getEndTime() + " with " + meeting.getParticipantOne() + "  and " + meeting.getParticipantTwo());

        }
        javaMailSender.send(mail);
    }
}
