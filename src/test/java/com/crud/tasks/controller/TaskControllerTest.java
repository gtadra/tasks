package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.service.DbService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TrelloController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;
    @MockBean
    private TaskController taskController;
    @MockBean
    private DbService dbService;


    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) //or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        // Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "Test list", "false"));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "Test Task", trelloLists));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                // Trello board fields
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test Task")))
        // Trello list fields
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name", Matchers.is("Test list")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed", Matchers.is("false")));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test", "Test description", "top", "1");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("232", "Test", "http://test.com");
        when(trelloFacade.createTrelloCard(any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("232")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl", Matchers.is("http://test.com")));
    }

    @Test
    void getAllTasksTest() throws Exception {
        //Given
        List<TaskDto> tasksDto = List.of(new TaskDto(10L, "Title", "Content"));
        when(taskController.getTasks()).thenReturn(tasksDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Content")));
    }

    @Test
    void getTaskWithSpecifiedIdTest() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(10L, "Title", "Content");
        when(taskController.getTask(10L)).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content")));
    }

    @Test
    void createTaskTest() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(10L, "Title", "Content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    void updateTaskTest() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(10L, "Title", "Content");
        when(taskController.updateTask(taskDto)).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    void deleteTaskTest() throws Exception {
        //Given
        doNothing().when(taskController).deleteTask(10L);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tasks/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}