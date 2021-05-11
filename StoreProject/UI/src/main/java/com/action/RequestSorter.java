package com.action;

import com.util.SpringContextUtil;
import com.action.requestAction.GetAllRequests;
import com.exception.ActionException;
import com.model.Request;
import com.util.comparator.RequestCounterComparator;
import com.util.comparator.RequestIdComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public RequestSorter(int sortId, String method){
        this.sortId = sortId;
        this.method = method;
        sortRequestsBy = new HashMap<>();
        sortRequestsBy.put(1, new RequestIdComparator());
        sortRequestsBy.put(2, new RequestCounterComparator());
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public void execute() {
        try {
            switch (method) {
                case "getAll":
                    setRequests(SpringContextUtil.getInstance().getBean(GetAllRequests.class).doIt());
                    break;
            }
            requests.sort(sortRequestsBy.get(sortId));
            LOGGER.info(requests.toString());
        } catch(ActionException e){
            LOGGER.warn("Method execute failed", e);
        }
    }
}
