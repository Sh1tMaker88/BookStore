package com.model;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ extends com.model.AIdentity_ {

	public static volatile ListAttribute<Order, Book> books;
	public static volatile SingularAttribute<Order, Double> totalPrice;
	public static volatile SingularAttribute<Order, LocalDateTime> orderDate;
	public static volatile SingularAttribute<Order, String> customerName;
	public static volatile SingularAttribute<Order, OrderStatus> status;
	public static volatile SingularAttribute<Order, LocalDateTime> dateOfDone;

	public static final String BOOKS = "books";
	public static final String TOTAL_PRICE = "totalPrice";
	public static final String ORDER_DATE = "orderDate";
	public static final String CUSTOMER_NAME = "customerName";
	public static final String STATUS = "status";
	public static final String DATE_OF_DONE = "dateOfDone";

}

