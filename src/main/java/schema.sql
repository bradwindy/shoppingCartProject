/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  bradley
 * Created: 7/08/2018
 */

create table Product (
    Product_ID integer not null,
    Product_Name varchar(50) not null,
    description varchar(200) not null,
    category varchar(50) not null,
    price decimal(10,2) not null,
    quantity integer not null,
    constraint Product_PK primary key (Product_ID)
);


create table Customer (
    personID integer auto_increment(1),
    username varchar(50) not null unique,
    firstname varchar(50) not null,
    surname varchar(50) not null,
    password varchar(50) not null,
    email varchar(50) not null,
    address varchar(200) not null,
    creditCardDetails varchar(50) not null,

    constraint Customer_PK primary key (personID)
);

create table Sale ( 
    saleID integer auto_increment(1), 
    date DATE not null, 
    status varchar(20) not null, 
    personid integer not null,

    constraint salePK primary key(saleID), 
    constraint sale_personID foreign key(personid) references customer 
);

create table SaleItem ( 
    saleID integer not null, 
    product_id integer not null, 
    quantity integer not null, 
    saleprice integer not null, 

    constraint saleItemPK primary key(saleID, product_id), 
    constraint saleitem_product_id foreign key(product_id) references product,
    constraint saleitem_saleID foreign key(saleID) references sale
);