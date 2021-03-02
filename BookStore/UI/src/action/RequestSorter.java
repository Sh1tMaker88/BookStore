package src.action;

import models.Request;
import src.action.requestActions.GetAllRequests;
import src.exceptions.ActionException;
import util.comparators.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestSorter implements IAction{

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
            e.printStackTrace();
        }
    }
}
