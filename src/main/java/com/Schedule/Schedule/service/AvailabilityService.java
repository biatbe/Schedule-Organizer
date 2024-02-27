package com.Schedule.Schedule.service;

import com.Schedule.Schedule.auth.AvailabilityRequest;
import com.Schedule.Schedule.user.Availability;
import com.Schedule.Schedule.user.AvailabilityRepository;
import com.Schedule.Schedule.user.User;
import com.Schedule.Schedule.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {
    private final UserRepository userRepository;
    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public AvailabilityService(UserRepository userRepository, AvailabilityRepository availabilityRepository) {
        this.userRepository = userRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public List<Availability> getUserAvailability(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The user does not exist!"));
        return availabilityRepository.findByUser(user);
    }

    public void updateUserAvailability(String userEmail, AvailabilityRequest availabilityRequest) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("The user does not exist!"));

        Availability availability = availabilityRepository.findByUser(user).get(0);
        if (availability == null) {
            availability = new Availability();
        }
        availability.setUser(user);
        availability.setDate(availabilityRequest.getDate());
        availability.setStartTime(availabilityRequest.getStartTime());
        availability.setEndTime(availabilityRequest.getEndTime());

        availabilityRepository.save(availability);
    }

}
