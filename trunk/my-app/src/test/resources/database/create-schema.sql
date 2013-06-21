begin
	drop table Person
	create table Person (id bigint generated by default as identity, name varchar(255), primary key (id))
	select person0_.id as id1_0_, person0_.name as name2_0_ from Person person0_
	insert into Person (id, name) values (default, ?)
	values identity_val_local()
end;