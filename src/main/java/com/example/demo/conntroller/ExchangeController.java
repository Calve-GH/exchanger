package com.example.demo.conntroller;

import com.example.demo.service.Loader;
import com.example.demo.service.RateService;
import com.example.demo.to.RateDTO;
import com.example.demo.utils.ExchangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.StringJoiner;

@RestController
@RequestMapping(value = ExchangeController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ExchangeController {
    public static final String REST_URL = "rest/exchange/";

    private RateService service;
    private Loader loader;

    @Autowired
    public ExchangeController(RateService service, Loader loader) {
        this.service = service;
        this.loader = loader;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public BigDecimal exchange(@RequestBody @Valid RateDTO dto, BindingResult validation) {
        if (validation.hasErrors()) {
            StringJoiner sj = new StringJoiner( " ", "Errors: ", ".");
            for (FieldError error : validation.getFieldErrors()) {
                sj.add(error.getField()).add(error.getDefaultMessage());
            }
            throw new ExchangeException(sj.toString());
        }
        return service.getExchangeResult(dto);
    }
    @GetMapping
    public void test() {

    }

    @ExceptionHandler(ExchangeException.class)
    public ResponseEntity<String> handleContentNotAllowedException(ExchangeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
