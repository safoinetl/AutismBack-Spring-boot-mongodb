package com.example.backend.Sevice;

import com.example.backend.DTO.ActivityDto;
import com.example.backend.DTO.ChildDto;
import com.example.backend.DTO.UserDto;
import com.example.backend.repository.*;
import com.example.backend.schema.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class responsableService {
    private final ChildRepository childRepository;
    private final UserRepository repository;
    private final groupRepository groupRepository;
    private final ActivityRepository activityRepository;
    private final NoteRepository noteRepository;


    private final UserRepository userRepository;


    public responsableService(ChildRepository childRepository, UserRepository repository, groupRepository groupRepository, ActivityRepository activityRepository, NoteRepository noteRepository, UserRepository userRepository) {
        this.childRepository = childRepository;
        this.repository = repository;
        this.groupRepository = groupRepository;
        this.activityRepository = activityRepository;
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public child addingChild(ChildDto request) {
        child newChild = new child();
        newChild.setName(request.getName());
        newChild.setDisabledCard(request.getDisabledCard());
        newChild.setLastName(request.getLastName());
        newChild.setAge(request.getAge());
        newChild.setBirthDate(request.getBirthDate());
        return childRepository.save(newChild);
    }

    public List<child> listChild() {
        return  this.childRepository.findAll();
    }

    public void  deleteChild (String id) {
        this.childRepository.deleteById(id);
    }
    public String addChildToGrp(child childId, User userId, groupDTO request) {
        Optional<child> searchChild = this.childRepository.findById(childId.getId());
        Optional<User> searchUser = this.repository.findById(userId.getId());
        if (searchUser.isEmpty() || searchChild.isEmpty()) {
            throw new UsernameNotFoundException("Something went wrong. Please check your inputs.");
        }
        else {
            child newChild = searchChild.get();
            User newUser = searchUser.get();
            group newGroup = new group();
            System.out.println(request);
            newGroup.setUser(newUser);
            newGroup.getChildren().add(newChild);
            newGroup.setNameG(request.getNameG());
            this.groupRepository.save(newGroup);
        }
        return "Group added successfully";
    }
    public group getGroupById(String id) {

    Optional<group> gp = this.groupRepository.findById(id);
    return gp.isPresent() ? gp.get(): null;

    }

    public List<activity> listActivity() {
        return  this.activityRepository.findAll();
    }
    public void deleteActivity(String id) {
        this.activityRepository.deleteById(id);
    }
    public activity addActivity(ActivityDto request) {
        activity newActivity = new activity();
        newActivity.setDescription(request.getDescription());
        newActivity.setStartingDate(request.getStartingDate());
        newActivity.setEndingDate(request.getEndingDate());
        return activityRepository.save(newActivity);
    }
    public User addUser(UserDto request) {
        User newUser= new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        return userRepository.save(newUser);
    }

    public List<User> listUser() {
        return  this.userRepository.findAll();
    }

    public void deleteUser(String id) {
        this.activityRepository.deleteById(id);
    }

    public List<note> listNote() {
        return  this.noteRepository.findAll();
    }
}

