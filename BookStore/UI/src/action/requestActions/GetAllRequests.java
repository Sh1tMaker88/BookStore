package src.action.requestActions;

import models.Request;
import src.Facade;

import java.util.List;

public class GetAllRequests {

    final Facade facade = Facade.getInstance();


    public List<Request> doIt() {
        List<Request> list = facade.getRequestService().getRequestDao().getAll();
        return list;
    }
}
