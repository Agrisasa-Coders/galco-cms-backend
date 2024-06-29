package com.gapco.backend.service;

import com.gapco.backend.entity.Message;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.MessageRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public CustomApiResponse<Object> saveMessage(Message message){
        log.info("MessageService::saveMessage Execution started");

        Message newMessage = new Message();
        newMessage.setFullName(message.getFullName());
        newMessage.setEmail(message.getEmail());
        newMessage.setCity(message.getCity());
        newMessage.setPhoneNumber(message.getPhoneNumber());
        newMessage.setMessage(message.getMessage());

        messageRepository.save(newMessage);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New message has been successfully saved");
        customApiResponse.setData(newMessage);

        //TODO Asynchronous sending to the email

        log.info("MessageService::saveMessage Execution ended");
        return customApiResponse;

    }


    public CustomApiResponse<Object> view(Long id) {

        Optional<Message> messageOptional = messageRepository.findById(id);

        if(messageOptional.isPresent()){
            Message existingMessage = messageOptional.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(existingMessage);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Message not found");
        }
    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Message> pageableMembers = messageRepository.findAll(pageable);

        List<Message> messages = pageableMembers.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableMembers.getTotalElements(),
                pageableMembers.getTotalPages(),
                pageableMembers.getNumber()

        );
        customApiResponse.setData(messages);
        return customApiResponse;
    }

}
