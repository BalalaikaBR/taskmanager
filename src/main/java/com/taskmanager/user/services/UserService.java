package com.taskmanager.user.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;
import com.taskmanager.user.dtos.UserDto;
import com.taskmanager.user.dtos.UserEventDto;
import com.taskmanager.user.utils.MissingBody;
import com.taskmanager.exceptions.GenericException;
import com.taskmanager.utils.MissingId;
import com.taskmanager.utils.Paginacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.taskmanager.user.utils.IsEmail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.taskmanager.rabbitmq.UserRabbitConfiguration;

@Service
public class UserService {
    private final UserRepository repo;
    private PasswordEncoder passwordEncoder;
    private UserRabbitConfiguration userRabbitConfiguration;
    private RabbitTemplate rabbitTemplate;
    private MissingId idIsMissing = new MissingId();
    private MissingBody missingBody = new MissingBody();
    private IsEmail email = new IsEmail();
    private GenericException error = new GenericException();

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.rabbitTemplate = rabbitTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserDto body) {

        try {
            User user = new User();
            missingBody.missingFields(body);
            email.isEmail(body.getEmail());
            user.setName(body.getName());
            user.setEmail(body.getEmail());
            user.setRole(body.getRole());
            user.setPassword(passwordEncoder.encode(body.getPassword()));
            repo.save(user);
            UserEventDto userEvent = new UserEventDto();
            userEvent.setId(user.getId());
            userEvent.setEmail(user.getEmail());
            rabbitTemplate.convertAndSend(
                    userRabbitConfiguration.EXCHANGE,
                    userRabbitConfiguration.ROUTING_KEY_CREATE,
                    userEvent);
            return user;
        } catch (Exception e) {
            return error.handleException(e);
        }
    }

    public User updateUser(UserDto body, String id) {
        idIsMissing.missingID(id);
        missingBody.missingFields(body);
        email.isEmail(body.getEmail());
        User user = repo.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setName(body.getName());
        user.setEmail(body.getEmail());
        user.setRole(body.getRole());
        user.setPassword(passwordEncoder.encode(body.getPassword()));

        repo.save(user);
        UserEventDto userEvent = new UserEventDto();
        userEvent.setId(user.getId());
        userEvent.setEmail(user.getEmail());

        rabbitTemplate.convertAndSend(
                UserRabbitConfiguration.EXCHANGE,
                userRabbitConfiguration.ROUTING_KEY_UPDATE,
                userEvent);
        return user;
    }

    public void deleteUser(String id) {

        idIsMissing.missingID(id);
        repo.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        repo.deleteById(id);
        UserEventDto userDeleted = new UserEventDto();
        userDeleted.setId(id);
        rabbitTemplate.convertAndSend(
                userRabbitConfiguration.EXCHANGE,
                userRabbitConfiguration.ROUTING_KEY_DELETE,
                userDeleted);
    }

    public User getUserById(String id) {
        try {
            idIsMissing.missingID(id);
            return repo.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        } catch (Exception e) {
            return error.handleException(e);
        }
    }

    public User patchUser(String id, UserDto body) {
        try {
            idIsMissing.missingID(id);

            User user = repo.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            if (body.getName() != null)
                user.setName(body.getName());
            if (body.getEmail() != null)
                user.setEmail(body.getEmail());
            if (body.getPassword() != null)
                user.setPassword(passwordEncoder.encode(body.getPassword()));
            if (body.getRole() != null)
                user.setRole(body.getRole());
            repo.save(user);
            UserEventDto userEvent = new UserEventDto();
            userEvent.setId(user.getId());
            userEvent.setEmail(user.getEmail());

            rabbitTemplate.convertAndSend(
                    UserRabbitConfiguration.EXCHANGE,
                    userRabbitConfiguration.ROUTING_KEY_UPDATE,
                    userEvent);
            return user;
        } catch (Exception e) {
            return error.handleException(e);
        }
    }

    public Page<User> getAllUser(int pageNum, int limitNum) {
        try {
            Pageable pageable = PageRequest.of(pageNum - 1, limitNum);
            return repo.findAll(pageable);
        } catch (Exception e) {
            return error.handleException(e);
        }
    }

}
