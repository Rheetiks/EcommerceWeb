create table products(
productId int auto_increment primary key,
sellerId int,
productNmae varchar(100),
productUrl varchar(200),
productDescription varchar(300),
productQuantity int,
foreign key (sellerId) references seller(sellerId)
);

select * from products;  

use ecom;

alter table products
RENAME column productNmae TO productName;

alter table products add categoryId int,
ADD CONSTRAINT FOREIGN KEY(categoryId) REFERENCES category(categoryId);

insert into products (productId,sellerId,productNmae,productUrl,productDescription,productQuantity) values ();

insert into products (sellerId,productNmae,productUrl,productDescription,productQuantity,productAmount,categoryId) values ((select sellerId from seller where sellerName='rohan'),'Football','fbhkjrhgkjrghrju','The ball is made of leather (possibly from a deer) and a pigs bladder It is roughly spherical with a diameter of between 14–16 cm',4,499,(select categoryId from category where categoryName='sports'));  

delete from products where productId=1;

