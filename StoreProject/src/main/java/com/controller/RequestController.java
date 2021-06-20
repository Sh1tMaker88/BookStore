package com.controller;

import com.api.service.IRequestService;
import com.controller.configuration.NoSuchEntityException;
import com.model.Request;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/requests")
public class RequestController {

    private final IRequestService requestService;

    @Autowired
    public RequestController(IRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Request>> showAllRequests() {
        log.info("Received GET request /requests");
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> showRequest(@PathVariable Long id) {
        log.info("Received GET request /requests/" + id);
        return ResponseEntity.ok(requestService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Request> saveRequest(@RequestBody Request request) {
        log.info("Received POST request /requests");
        requestService.saveRequest(request);
        return ResponseEntity.ok(request);
    }

    @PutMapping("/")
    public ResponseEntity<Request> updateRequest(@RequestBody Request request) {
        log.info("Received PUT request /requests");
        requestService.saveRequest(request);
        return ResponseEntity.ok(request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request request) {
        log.info("Received PATCH request /requests/" + id);
        requestService.saveRequest(request);
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public String deleteRequest(@PathVariable Long id) {
        log.info("Received DELETE request /requests/" + id);
        if (requestService.getById(id) == null) {
            throw new NoSuchEntityException("There is no order with id=" + id + " in database");
        } else {
            requestService.deleteRequest(id);
            return "Order with id=" + id + " was deleted";
        }
    }

    @PutMapping("/close-request/{id}")
    public ResponseEntity<Request> closeRequest(@PathVariable Long id) {
        log.info("Received PUT request /requests/close-request/" + id);
        return ResponseEntity.ok(requestService.closeRequest(id));
    }

    @PostMapping("/add-request/{id}")
    public ResponseEntity<Request> addRequest(@PathVariable Long id) {
        log.info("Received POST request /requests/add-request/" + id);
        return ResponseEntity.ok(requestService.addRequest(id));
    }


}
