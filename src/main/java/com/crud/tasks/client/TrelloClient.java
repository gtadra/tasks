package com.crud.tasks.client;

import com.crud.tasks.domain.TrelloBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUserName;


    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = generateTrelloUri();

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);


        List<TrelloBoardDto> resultList =  Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());

        return resultList.stream()
                .filter(n -> n.getName().contains("Kodilla"))
                .collect(Collectors.toList());

    }

    private URI generateTrelloUri(){
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/"+ trelloUserName +"/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .build()
                .encode()
                .toUri();
    }

}
