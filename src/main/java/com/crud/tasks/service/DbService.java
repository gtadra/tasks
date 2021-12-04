package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;

    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    public Task getTaskById(Long id){
        Optional<Task> result = repository.findById(id);
        Task resultTask = new Task();

        if(result.isPresent()){
            resultTask = new Task(result.get().getId(), result.get().getTitle(), result.get().getContent());
        }

        return resultTask;
    }
}
