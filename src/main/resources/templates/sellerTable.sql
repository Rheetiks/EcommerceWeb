use Ecom;

create table seller(
sellerId int auto_increment primary key,
sellerName varchar(30),
sellerPhoneNo long ,
sellerEmail varchar(40),
sellerPassword varchar(20) 
);


select * from seller;

insert into seller (sellerName,sellerPhoneNo,sellerEmail,sellerPassword) values ('rohan',9012345678,'rohan694@gmail.com','sharma');