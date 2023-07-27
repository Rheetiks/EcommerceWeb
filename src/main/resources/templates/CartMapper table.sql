create table CartMapper(
userId int,
productId int,
foreign key(userId) references user(userId),
foreign key(productId) references products(productId)
);

use ecom;

alter table CartMapper alter cartProductQuantity set default(1); 

select * from CartMapper;

insert into CartMapper(userId,productId) values(2,4);

select * from products where productId in(select productId from CartMapper where userId=1);

select count(ProductId) as productId from CartMapper where userId=1;

insert into CartMapper(userId,productId) values(1,4);

update CartMapper set cartProductQuantity=1 where productId=2;

delete from CartMapper where userId=2;