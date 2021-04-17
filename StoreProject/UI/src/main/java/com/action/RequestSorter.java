package com.action;

import com.action.requestActions.GetAllRequests;
import com.exceptions.ActionException;
import com.models.Request;
import com.util.comparators.RequestAlphabeticalComparator;
import com.util.comparators.RequestIdComparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestSorter implements IAction{

    private static final Logger LOGGER = Logger.getLogger(RequestSorter.class.getName());
    private Map<Integer, Comparator<Request>> sortRequestsBy;
    private List<Request> requests;
    private int sortId;
    String method;

    public RequestSorter(int sortId, String method){
        this.sortId = sortId;
        this.method = method;
        sortRequestsBy = new HashMap<>();
        sortRequestsBy.put(1, new RequestIdComparator());
        sortRequestsBy.put(2, new RequestAlphabeticalComparator());
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public void execute() {
        try {
            switch (method) {
                case "getAll":
                    setRequests(new GetAllRequests().doIt());
                    break;
            }

            requests.sort(sortRequestsBy.get(sortId));
            System.out.println(requests);
        } catch(ActionException e){
            LOGGER.log(Level.WARNING, "Method execute failed", e);
        }
    }
}
