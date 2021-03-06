package com.tosh.reactivespring.handler;

import com.tosh.reactivespring.document.Item;
import com.tosh.reactivespring.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ItemHandler {

    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getAllItems(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemReactiveRepository.findAll(), Item.class);
    }

    public Mono<ServerResponse> getItemById(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        Mono<Item> itemMono = itemReactiveRepository.findById(id);
        return itemMono.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(item))
                        .switchIfEmpty(notFound)
        );
    }

    public Mono<ServerResponse> createItem(ServerRequest serverRequest) {
        Mono<Item> itemToInsert = serverRequest.bodyToMono(Item.class);

        return itemToInsert.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(itemReactiveRepository.save(item), Item.class)
        );
    }

    public Mono<ServerResponse> deleteItemById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Void> deletedItem = itemReactiveRepository.deleteById(id);

        return deletedItem.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(deletedItem, void.class)
        );
    }

    public Mono<ServerResponse> updateItem(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Item> itemToInsert = serverRequest.bodyToMono(Item.class)
                .flatMap(item -> {
                    Mono<Item> itemMono = itemReactiveRepository.findById(id)
                            .flatMap(currentItem -> {
                                currentItem.setDescription(item.getDescription());
                                currentItem.setPrice(item.getPrice());
                                return itemReactiveRepository.save(currentItem);
                            });
                    return itemMono;
                });

        return itemToInsert.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(item))
                        .switchIfEmpty(notFound)
        );
    }

    public Mono<ServerResponse> itemsEx(ServerRequest serverRequest) {
        throw  new RuntimeException("KEKE");
    }
}
