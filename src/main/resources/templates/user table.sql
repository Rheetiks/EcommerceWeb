create table user(
userId int auto_increment primary key,
userName varchar(20),
userPhoneNo long,
userEmail varchar(30),
userPassword varchar(20),
userAddress varchar(150)
);


select * from user;

use Ecom;

insert into user(userName,userPhoneNo,userEmail,userPassword) values();

delete from user where userId=4;