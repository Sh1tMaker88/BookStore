package com.controller;

import com.api.service.IRequestService;
import com.controller.configuration.NoSuchEntityException;
import com.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestController {

    private final IRequestService requestService;

    @Autowired
    public RequestController(IRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    public List<Request> showAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/requests/{id}")
    public Request showRequest(@PathVariable Long id) {
        return requestService.getById(id);
    }

    @PostMapping("/requests")
    public Request saveRequest(@RequestBody Request request) {
        requestService.saveRequest(request);
        return request;
    }

    @PutMapping("/requests")
    public Request updateRequest(@RequestBody Request request) {
        requestService.saveRequest(request);
        return request;
    }

    @DeleteMapping("/requests/{id}")
    public String deleteRequest(@PathVariable Long id) {
        if (requestService.getById(id) == null) {
            throw new NoSuchEntityException("There is no order with id=" + id + " in database");
        } else {
            requestService.deleteRequest(id);
            return "Order with id=" + id + " was deleted";
        }
    }
}
