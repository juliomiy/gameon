/*
  Author: Julio Hernandez-Miyares
  Date: April 4,2010 
*/

DELIMITER //

use jittrgameon
//
/* listing of all public games - college and professional */
drop table if exists go_publicgames
//
create table siteConfiguration (
   site varchar(25) not null,
   payPalBusinessAccount varchar(255) ,
   payPalBusinessPW varchar(255),
   payPalApiSignature varchar(255),
   Primary Key (site)
)
ENGINE=INNODB

create table go_publicgames (
   id 
   title varchar(50) not null,
   eventName varchar(50) null,
   date timestamp null,
   description varchar(255) null,
   type  int not null default 0,
   sport int not null default 0,
   PRIMARY KEY (id)
)
ENGINE=INNODB
//


/* Lookup tables */
/* Professional and Colleigette teams */
drop table if exists go_teams_lu
//
create table go_teams_lu (
  id int not null AUTO_INCREMENT,
  teamName varchar(50) not null,
  createdDate timestamp not null default current_timestamp(),
  modifiedDate timestamp null,
  PRIMARY KEY(id),
  UNIQUE INDEX(teamName)
)
ENGINE=INNODB

go_types_lu
go_sports_lu


