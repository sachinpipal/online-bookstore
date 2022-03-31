Create table book (
id serial not null,
author varchar(255),
isbn varchar(255),
price float8,
quantity int4,
title varchar(255),
primary key (id)
) ;
create table ordertbl (
  id serial not null, 
  order_id varchar(255), 
  quantity int4, 
  book_id int4, 
  primary key (id)
) ;
alter table 
  ordertbl 
add 
  constraint FKtmj55ae9ycp2c7gf66l5d7ewu foreign key (book_id) references book;
