package src.action;

import models.Request;
import src.action.requestActions.GetAllRequests;
import util.comparators.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestSorter implements IAction{

    private Map<Integer, Comparator<Request>> sortRequestsBy;
    private List<Request> requests;
    private int id;
    String method;

    public RequestSorter(int id, String method){
        this.id = id;
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
        switch (method) {
            case "getAll":
                setRequests(new GetAllRequests().doIt());
                break;
        }

        requests.sort(sortRequestsBy.get(id));
        System.out.println(requests);
    }
}
