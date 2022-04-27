package com.example.springangularadsapp.components.message;

import com.example.springangularadsapp.components.message.assembler.MessageModelAssembler;

import com.example.springangularadsapp.components.exceptions.MessageNotFoundException;
import com.example.springangularadsapp.constants.HateoasController;
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
public class MessageController implements HateoasController<Message, MessageDto> {
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


    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody MessageDto newEntity) {
        //EntityModel<Message> entityModel = this.assembler.toModel(this.messageRepository.save(newEntity));
        //return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        return null;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody MessageDto newEntity, @PathVariable String id) {
        Message updatedMsg = this.messageRepository.findById(id).map(msg -> {
            msg.setMessage(newEntity.getMessage());
            return this.messageRepository.save(msg);
        }).orElseGet(() -> {

            return null;
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
