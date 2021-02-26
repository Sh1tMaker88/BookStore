package src.action;

import models.Request;
import util.comparators.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestSorter implements IAction{

    private Map<Integer, Comparator<Request>> sortRequestsBy;
    private List<Request> requests;
    private int id;

    public RequestSorter(int id, List<Request> requests){
        this.id = id;
        this.requests = requests;
        sortRequestsBy = new HashMap<>();
        sortRequestsBy.put(1, new RequestIdComparator());
        sortRequestsBy.put(2, new RequestAlphabeticalComparator());
    }

    @Override
    public void execute() {

        requests.sort(sortRequestsBy.get(id));
        System.out.println(requests);
    }
}
