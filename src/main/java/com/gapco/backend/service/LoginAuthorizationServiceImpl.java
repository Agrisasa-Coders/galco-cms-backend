package com.gapco.backend.service;

import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.ConfigurationRepository;
import com.gapco.backend.repository.UserRepository;
import com.gapco.backend.entity.Configuration;
import com.gapco.backend.entity.User;
import com.gapco.backend.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginAuthorizationServiceImpl implements LoginAuthorizationService{

    private final UserRepository userRepository;
    private final ConfigurationRepository configurationRepository;

    @Override
    public boolean isNoOfPasswordAttemptsAllowed(Long institutionId, int noOfPasswordAttempts) {
        log.info("LoginAuthorizationServiceImpl::isNoOfPasswordAttemptsAllowed Execution started");
        log.info("LoginAuthorizationServiceImpl::isNoOfPasswordAttemptsAllowed institutionId is {}",institutionId);

        Configuration configuration = configurationRepository.findInstitutionConfiguration(institutionId)
                .orElseThrow(()->new EntityNotFoundException("Configuration for the user's institution is not found"));

        if(noOfPasswordAttempts >= configuration.getMaxPasswordAttempts())
            return false;
        else
            return true;
    }

    @Override
    public boolean isPasswordNotExpired(Long institutionId,LocalDateTime lastPasswordUpdateTime) {

        Configuration configuration = configurationRepository.findInstitutionConfiguration(institutionId)
                .orElseThrow(()->new EntityNotFoundException("Configuration for the user's institution is not found"));

        LocalDateTime currentTime = Helper.getCurrentTime();

        long differenceInMonths = lastPasswordUpdateTime.until(currentTime, ChronoUnit.MONTHS);

        if(differenceInMonths > configuration.getMaxPasswordExpiryMonths())
            return false;
        else
            return true;
    }

    @Override
    public void incrementPasswordAttempt(User user) {
        int noOfPasswordAttempt = user.getNoOfPasswordAttempts();
        user.setNoOfPasswordAttempts(noOfPasswordAttempt+1);
        userRepository.save(user);
    }

    @Override
    public void decrementPasswordAttempt(User user) {
        int noOfPasswordAttempt = user.getNoOfPasswordAttempts();

        noOfPasswordAttempt-=1;

        if(noOfPasswordAttempt < 0){
            noOfPasswordAttempt = 0;
        }

        user.setNoOfPasswordAttempts(noOfPasswordAttempt);
        userRepository.save(user);
    }

    @Override
    public void resetPasswordAttempts(User user) {
        user.setNoOfPasswordAttempts(0);
        userRepository.save(user);
    }
}
