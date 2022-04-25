package com.example.springangularadsapp.controller;

import com.example.springangularadsapp.assembler.MessageModelAssembler;

import com.example.springangularadsapp.exceptions.MessageNotFoundException;
import com.example.springangularadsapp.models.Message;
import com.example.springangularadsapp.repository.MessageRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/messages")
public class MessageController implements HateoasController<Message> {
    private final MessageRepository messageRepository;
    private final MessageModelAssembler assembler;

    public MessageController(MessageRepository messageRepository, MessageModelAssembler assembler) {
        this.messageRepository = messageRepository;
        this.assembler = assembler;
    }

    @Override
    @GetMapping("/")
    public CollectionModel<EntityModel<Message>> all() {
        List<EntityModel<Message>> messages = this.messageRepository.findAll().stream().map(this.assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(messages, linkTo(methodOn(MessageController.class).all()).withSelfRel());
    }

    @Override
    @GetMapping("/{id}")
    public EntityModel<Message> one(@PathVariable String id) {
        Message message = this.messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
        return this.assembler.toModel(message);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Message newEntity) {
        EntityModel<Message> entityModel = this.assembler.toModel(this.messageRepository.save(newEntity));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Message newEntity, @PathVariable String id) {
        Message updatedMsg = this.messageRepository.findById(id).map(msg -> {
            msg.setMessage(newEntity.getMessage());
            msg.setFrom(newEntity.getFrom());
            msg.setTo(newEntity.getTo());
            msg.setAd(newEntity.getAd());
            return this.messageRepository.save(msg);
        }).orElseGet(() -> {
            newEntity.setId(id);
            return this.messageRepository.save(newEntity);
        });

        EntityModel<Message> entityModel = this.assembler.toModel(updatedMsg);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        this.messageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
