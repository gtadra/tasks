package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MappersTestSuite {

    @Test
    void testTaskMapper(){
        //Given
        Long id = 1000L;
        String title = "title";
        String content = "content";
        Task task = new Task(id,title,content);
        TaskDto taskDto = new TaskDto(id,title,content);
        TaskMapper taskMapper = new TaskMapper();
        //When
        Task taskResult = taskMapper.mapToTask(taskDto);
        TaskDto taskDtoResult = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(task.getContent(),taskResult.getContent());
        assertEquals(task.getId(),taskResult.getId());
        assertEquals(task.getTitle(),taskResult.getTitle());
        assertEquals(taskDto.getContent(),taskDtoResult.getContent());
        assertEquals(taskDto.getId(),taskDtoResult.getId());
        assertEquals(taskDto.getTitle(),taskDtoResult.getTitle());
    }

    @Test
    void testTaskListMapper(){
        //Given
        Long id = 1000L;
        String title = "title";
        String content = "content";
        List<Task> taskList = List.of(new Task(id,title,content));
        List<TaskDto> taskDtoList = List.of(new TaskDto(id,title,content));
        TaskMapper taskMapper = new TaskMapper();
        //When
        List<TaskDto> taskDtoListResult = taskMapper.mapToTaskDtoList(taskList);
        //Then
        taskDtoListResult.forEach(trelloDtos -> {
            assertThat(trelloDtos.getTitle()).isEqualTo("title");
            assertThat(trelloDtos.getId()).isEqualTo(1000L);
            assertThat(trelloDtos.getContent()).isEqualTo("content");
        });



    }

}
