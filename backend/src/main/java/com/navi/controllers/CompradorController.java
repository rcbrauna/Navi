package com.navi.controllers;

import com.navi.api.SMSApi;
import com.navi.models.Comprador;
import com.navi.repositories.CompradorRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "localhost:3000")
public class CompradorController {

    @Autowired
    private CompradorRepository repository;


    @PostMapping("/cadastro/comprador")
    public ResponseEntity createComprador(@RequestBody Comprador novoComprador) {
        repository.save(novoComprador);

//        Twilio.init(SMSApi.getAccountSid(), SMSApi.getAuthToken());
//        Message message = Message.creator(
//                new com.twilio.type.PhoneNumber("+12183878263"),
//                new com.twilio.type.PhoneNumber(novoComprador.getTelefone()),
//                "Olá" + novoComprador.getNome() + ", seja bem-vindo a Navi").create();
//
//        System.out.println(message.getSid());
        return ResponseEntity.ok(novoComprador);
    }

    @GetMapping("/compradores")
    public ResponseEntity getCompradores() {
        if (repository.findAll().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(repository.findAll());
        }
    }

    @GetMapping("/comprador/{cpf}")
    public ResponseEntity getCompradorByCpf(
            @RequestBody String cpf) {
        Comprador search;
        if (repository.findByCpf(cpf).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            search = repository.findByCpf(cpf).get(0);
            return ResponseEntity.ok(search);
        }
    }

    @PutMapping("/comprador/{cpf}/atualizar")
    public ResponseEntity updateComprador(
            @PathVariable String cpf,
            @RequestBody Comprador compradorAtualizado) {

        Comprador comprador = repository.findByCpf(cpf).get(0);

        comprador.setNome(compradorAtualizado.getNome());
        comprador.setEmail(compradorAtualizado.getEmail());
        comprador.setSenha(compradorAtualizado.getSenha());
        comprador.setCpf(compradorAtualizado.getCpf());
        comprador.setEndereco(compradorAtualizado.getEndereco());

        repository.save(comprador);
        return ResponseEntity.ok(comprador);


    }

    @DeleteMapping("/comprador/{cpf}/excluir")
    public ResponseEntity deleteComprador( @PathVariable String cpf) {
        Comprador search = repository.findByCpf(cpf).get(0);

        repository.delete(search);
        return ResponseEntity.ok(search);
    }

}
