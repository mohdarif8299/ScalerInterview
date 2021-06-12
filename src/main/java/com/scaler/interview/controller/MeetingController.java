package com.scaler.interview.controller;

import com.scaler.interview.model.Meeting;
import com.scaler.interview.model.ResponseObject;
import com.scaler.interview.model.User;
import com.scaler.interview.model.User_Meeting;
import com.scaler.interview.repository.MeetingRepository;
import com.scaler.interview.repository.UserMeetingRepository;
import com.scaler.interview.repository.UserRepository;
import com.scaler.interview.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMeetingRepository userMeetingRepository;

    @Autowired
    private MailService mailService;

    @GetMapping("/findAll")
    public ResponseEntity<?> getAllMeeting() {
        List<Meeting> meetingList = meetingRepository.findAll();
        return new ResponseEntity<>(meetingList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMeeting(@RequestBody Meeting meeting) {
        User user1 = userRepository.findByEmailId(meeting.getParticipantOne()).get();
        User user2 = userRepository.findByEmailId(meeting.getParticipantTwo()).get();
        if (meeting != null) {
            Meeting m = meetingRepository.save(meeting);

            // Participant 1 Insertion
            User_Meeting user_meeting = new User_Meeting();
            user_meeting.setMeetingId(m.getMeetingId());
            user_meeting.setEmail(m.getParticipantOne());
            userMeetingRepository.save(user_meeting);


            // Participant 2 Insertion
            User_Meeting user_meeting2 = new User_Meeting();
            user_meeting2.setMeetingId(m.getMeetingId());
            user_meeting2.setEmail(m.getParticipantTwo());
            userMeetingRepository.save(user_meeting2);

            // Mail TO Participants
            sendMail(meeting, false);

            return new ResponseEntity<>(new ResponseObject("Interview Scheduled Successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseObject("Something went wrong!"), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMeeting(@RequestBody Meeting meeting) {
        System.out.println("Meeting to Be UPdated " + meeting);
        Optional<Meeting> meetingExisting = meetingRepository.findByMeetingId(meeting.getMeetingId());
        if (meetingExisting.isPresent()) {

            Meeting updatedMeeting = meetingExisting.get();
            updatedMeeting.setStartTime(meeting.getStartTime());
            updatedMeeting.setEndTime(meeting.getEndTime());
            updatedMeeting.setParticipantOne(meeting.getParticipantOne());
            updatedMeeting.setParticipantTwo(meeting.getParticipantTwo());
            updatedMeeting.setAgenda(meeting.getAgenda());
            Meeting update = meetingRepository.save(updatedMeeting);
            if (update != null) {
                sendMail(meeting, true);
                return ResponseEntity.ok().body(new ResponseObject("Updated Meeting with Id : " + updatedMeeting.getMeetingId()));
            }
            return ResponseEntity.ok().body(new ResponseObject("Something Went wrong"));

        }
        return ResponseEntity.ok(new ResponseObject("No Meeting found to Update"));

    }

    @GetMapping("/getusers")
    public ResponseEntity<?> getUsers(@RequestParam String startTime, @RequestParam String endTime) {
        Set<User> userList = new HashSet<>(userRepository.findAll());
        List<User_Meeting> userMeetingList = userMeetingRepository.findAll();
        for (User_Meeting user_meeting : userMeetingList) {

            Meeting meeting = meetingRepository.findByMeetingId(user_meeting.getMeetingId()).get();
            User user = userRepository.findByEmailId(user_meeting.getEmail()).get();
            if (meeting.getEndTime().compareTo(startTime) < 0 || meeting.getStartTime().compareTo(endTime) > 0) {
                continue;
            } else {
                userList.remove(user);
            }
        }

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    public void sendMail(Meeting meeting, boolean update) {
        mailService.sendEmail(meeting, update);
    }

    @DeleteMapping("/delete/{meetingId}")
    private ResponseEntity<ResponseObject> deleteMeeting(@PathVariable Long meetingId) {
        Optional<Meeting> meeting = meetingRepository.findByMeetingId(meetingId);
        if (meeting.isPresent()) {
            meetingRepository.delete(meeting.get());
            return new ResponseEntity<>(new ResponseObject("Meeting Deleted"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseObject("Invalid Meeting ID"), HttpStatus.OK);
    }
}
