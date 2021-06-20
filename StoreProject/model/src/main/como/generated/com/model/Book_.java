package com.model;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ extends com.model.AIdentity_ {

	public static volatile SingularAttribute<Book, Request> request;
	public static volatile SingularAttribute<Book, BookStatus> bookStatus;
	public static volatile SingularAttribute<Book, Integer> pageNumber;
	public static volatile SingularAttribute<Book, String> author;
	public static volatile SingularAttribute<Book, Double> price;
	public static volatile SingularAttribute<Book, String> isbn;
	public static volatile SingularAttribute<Book, String> name;
	public static volatile SingularAttribute<Book, Integer> orderCount;
	public static volatile SingularAttribute<Book, String> description;
	public static volatile ListAttribute<Book, Order> orders;
	public static volatile SingularAttribute<Book, Integer> yearOfPublish;
	public static volatile SingularAttribute<Book, LocalDate> arrivalDate;

	public static final String REQUEST = "request";
	public static final String BOOK_STATUS = "bookStatus";
	public static final String PAGE_NUMBER = "pageNumber";
	public static final String AUTHOR = "author";
	public static final String PRICE = "price";
	public static final String ISBN = "isbn";
	public static final String NAME = "name";
	public static final String ORDER_COUNT = "orderCount";
	public static final String DESCRIPTION = "description";
	public static final String ORDERS = "orders";
	public static final String YEAR_OF_PUBLISH = "yearOfPublish";
	public static final String ARRIVAL_DATE = "arrivalDate";

}

