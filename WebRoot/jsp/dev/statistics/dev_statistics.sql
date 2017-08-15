create table test_statistics(
	id varchar(32) primary key,
	charttitle varchar(50),
	xtitle varchar(50),
	ytitle varchar(50),
	charttype int,
	transposed bit,
	sqlcommand varchar(4000),
	status char(1),
	create_time datetime
)
