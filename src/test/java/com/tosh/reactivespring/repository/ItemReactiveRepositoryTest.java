package com.tosh.reactivespring.repository;

import com.tosh.reactivespring.document.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ItemReactiveRepositoryTest {
    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    List<Item> itemList = Arrays.asList(new Item(null, "Samsung TV", 400.0),
            new Item(null, "LG TV", 420.0),
            new Item(null, "Sony TV", 450.0),
            new Item("ABC", "Bose Headphones", 430.0));

    @Before
    public void setup() {
        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(itemList))
                .flatMap(itemReactiveRepository::save)
                .blockLast();
    }

    @Test
    public void getAllItems() {
        StepVerifier.create(itemReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void getItemById() {
        StepVerifier.create(itemReactiveRepository.findById("ABC"))
                .expectSubscription()
                .expectNextMatches(item -> item.getDescription().equals("Bose Headphones"))
                .verifyComplete();
    }

    @Test
    public void findItemByDescription() {
        StepVerifier.create(itemReactiveRepository.findByDescription("LG TV"))
                .expectSubscription()
                .expectNextMatches(item -> item.getDescription().equals("LG TV"))
                .verifyComplete();
    }

    @Test
    public void saveItem() {
        Item item = new Item(null, "Nintendo Switch", 300.0);

        Mono<Item> saveItem = itemReactiveRepository.save(item);

        StepVerifier.create(saveItem)
                .expectSubscription()
                .expectNextMatches(i -> i.getDescription().equals("Nintendo Switch"))
                .verifyComplete();
    }

    @Test
    public void updateItem(){
        double newPrice = 520.0;
        Mono<Item> updatedItem = itemReactiveRepository.findByDescription("LG TV")
                .map( item -> {
                    item.setPrice(newPrice);
                    return item;
                })
                .flatMap(i -> itemReactiveRepository.save(i));

        StepVerifier.create(updatedItem)
                .expectSubscription()
                .expectNextMatches(i -> i.getPrice() == (newPrice))
                .verifyComplete();
    }

    @Test
    public void deleteItemById(){
        Mono<Void> updatedItem = itemReactiveRepository.findById("ABC")
                .map(Item::getId)
                .flatMap((id) -> itemReactiveRepository.deleteById(id)
                );

        StepVerifier.create(updatedItem)
                .expectSubscription()
                .verifyComplete();
    }

}