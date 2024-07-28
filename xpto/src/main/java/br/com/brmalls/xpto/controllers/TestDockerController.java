package br.com.brmalls.xpto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "docker" )
public class TestDockerController {

    private static final String message = "Testing container of the docker.";

    @GetMapping( "/testing-contianer" )
    @ResponseBody
    public ResponseEntity<String> container() {
        return ResponseEntity.ok( message );
    }

}
