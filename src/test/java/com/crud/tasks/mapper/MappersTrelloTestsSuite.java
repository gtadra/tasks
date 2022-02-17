package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MappersTrelloTestsSuite {


    @Test
    void trelloCardMapperTest(){
        //Given
        String name = "Test name";
        String description = "Test description";
        String pos = "Test pos";
        String listId = "Test listId";
        TrelloCardDto trelloCardDto = new TrelloCardDto(name, description, pos, listId);
        TrelloCard trelloCard = new TrelloCard(name, description, pos, listId);
        TrelloMapper trelloMapper = new TrelloMapper();
        //When
        TrelloCardDto resultTrelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        TrelloCard resultTrelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertTrue(trelloCardDto.equals(resultTrelloCardDto));
        assertTrue(trelloCard.equals(resultTrelloCard));
    }

    @Test
    void trelloListMapperTest(){
        //Given
        String id = "11237";
        String name = "Test name";
        String isClosedDto = "true";
        boolean isClosed = true;
        TrelloList trelloList = new TrelloList(id,name,isClosed);
        TrelloListDto trelloListDto = new TrelloListDto(id,name,isClosedDto);
        List<TrelloList> trelloLists = new ArrayList<>();
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloLists.add(trelloList);
        trelloLists.add(trelloList);
        trelloListDtos.add(trelloListDto);
        trelloListDtos.add(trelloListDto);
        TrelloMapper trelloMapper = new TrelloMapper();
        //When
        List<TrelloList> resultTrelloLists = trelloMapper.mapToList(trelloListDtos);
        List<TrelloListDto> resultTrelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        System.out.println("trelloList: " + trelloList);
        System.out.println("trelloListDto: " + trelloListDto);


        assertTrue(Objects.equals(trelloLists, resultTrelloLists));
        assertTrue(trelloListDtos.equals(resultTrelloListDtos));
    }

    @Test
    void trelloBoardMapperTest(){
        //Given
        String id = "11237";
        String name = "Test name";
        String isClosedDto = "true";
        boolean isClosed = true;
        TrelloList trelloList = new TrelloList(id,name,isClosed);
        TrelloListDto trelloListDto = new TrelloListDto(id,name,isClosedDto);
        List<TrelloList> trelloLists = new ArrayList<>();
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloLists.add(trelloList);
        trelloLists.add(trelloList);
        trelloListDtos.add(trelloListDto);
        trelloListDtos.add(trelloListDto);
        TrelloBoard trelloBoard = new TrelloBoard(id, name, trelloLists);
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard);
        trelloBoardList.add(trelloBoard);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto(id, name, trelloListDtos);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);
        trelloBoardDtoList.add(trelloBoardDto);
        TrelloMapper trelloMapper = new TrelloMapper();
        //When
        List<TrelloBoard> trelloBoardListResult = trelloMapper.mapToBoards(trelloBoardDtoList);
        List<TrelloBoardDto> trelloBoardDtoListResult = trelloMapper.mapToBoardsDto(trelloBoardList);
        //Then
        assertTrue(trelloBoardDtoList.equals(trelloBoardDtoListResult));
        assertTrue(trelloBoardList.equals(trelloBoardListResult));

    }



}
