package com.carreiras.hateoas.rest.controller;

import com.carreiras.hateoas.rest.assemblers.ActorModelAssembler;
import com.carreiras.hateoas.domain.entity.Actor;
import com.carreiras.hateoas.domain.repository.ActorRepository;
import com.carreiras.hateoas.rest.dto.ActorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Ewerton on 20-10-22
 */
@RestController
@RequestMapping(value = "/api/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ActorModelAssembler actorModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<ActorModel>> getAllActors() {
        List<Actor> actors = actorRepository.findAll();
        return new ResponseEntity<>(actorModelAssembler.toCollectionModel(actors), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorModel> getActorById(@PathVariable("id") Long id) {
        return actorRepository.findById(id)
                .map(actorModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}