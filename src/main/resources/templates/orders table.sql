create table orders(
orderId int auto_increment primary key,
userId int,
productId int,
foreign key (userId) references user(userId),
foreign key (productId) references products(productId)
);

select * from orders;