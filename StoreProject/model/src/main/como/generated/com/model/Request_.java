package com.model;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Request.class)
public abstract class Request_ extends com.model.AIdentity_ {

	public static volatile SingularAttribute<Request, Integer> requestCount;
	public static volatile SingularAttribute<Request, Book> book;
	public static volatile SingularAttribute<Request, LocalDateTime> requestDate;
	public static volatile SingularAttribute<Request, RequestStatus> requestStatus;

	public static final String REQUEST_COUNT = "requestCount";
	public static final String BOOK = "book";
	public static final String REQUEST_DATE = "requestDate";
	public static final String REQUEST_STATUS = "requestStatus";

}

