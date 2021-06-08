package com.action;

import com.action.requestAction.GetAllRequests;
import com.action.util.SpringContextHolder;
import com.exception.ActionException;
import com.model.Request;
import com.util.comparator.RequestCounterComparator;
import com.util.comparator.RequestIdComparator;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestSorter implements IAction{

    private static final Logger LOGGER = LogManager.getLogger(RequestSorter.class.getName());
    private Map<Integer, Comparator<Request>> sortRequestsBy;
    private List<Request> requests;
    private int sortId;
    String method;
    private final ApplicationContext context;

    public RequestSorter(int sortId, String method){
        this.sortId = sortId;
        this.method = method;
        sortRequestsBy = new HashMap<>();
        sortRequestsBy.put(1, new RequestIdComparator());
        sortRequestsBy.put(2, new RequestCounterComparator());
        this.context = SpringContextHolder.getApplicationContext();
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public void execute() {
        try {
            switch (method) {
                case "getAll":
                    setRequests(context.getBean(GetAllRequests.class).doIt());
                    break;
            }
            requests.sort(sortRequestsBy.get(sortId));
            LOGGER.info(requests.toString());
        } catch(ActionException e){
            LOGGER.warn("Method execute failed", e);
        }
    }
}
