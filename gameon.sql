/*
  Author: Julio Hernandez-Miyares
  Date: April 4,2010 

  Normalized tables will not be used for direct query from the applications
  either denormalized mysql tables or from lucene index or from a denormalized
  cache layer.
  These tables will be used as master persistence managed by CMS and automated ingestion tools/processes
*/

DELIMITER //

use jittrgameon
//
/* listing of all public games - college and professional */
drop table if exists go_publicgames
//
create table go_publicgames (
   id int not null AUTO_INCREMENT, 
   title varchar(50) not null,
   eventName varchar(50) null,
   date timestamp null,
   description varchar(255) null,
   type  int not null default 0,
   sport int not null default 0,
   createdDate timestamp not null default current_timestamp(),
   modifiedDate timestamp null,
   PRIMARY KEY (id),
   UNIQUE INDEX(title)
)
ENGINE=INNODB
//
/* Denormalized version of go_publicgames
   This is the table to be used to data power the webservice used from the Appication
   to return public games 
*/
drop table if exists go_publicgames_dn
//
create table go_publicgames_dn (
   id int not null,
   title varchar(50) not null,
   date timestamp null,
   typeName varchar(50) not null,
   teamName1 varchar(50) null,
   teamName2 varchar(50) null,
   sportName varchar(50) not null,
   leagueName varchar(50) not null,
   createdDate timestamp not null default current_timestamp(),
   modifiedDate timestamp null,
   PRIMARY KEY (id)
)
ENGINE=INNODB
//
/* Table which defines the "teams" or "individuals" that are part of an event such as type "team" or "tournament"
   id is used as a key to id field in go_publicgames table
   teamID is a key to the id field in go_teams_lu  table
*/
drop table if exists go_publicgames_combatants
//
create table go_publicgames_combatants (
   gameID int not null,
   teamID int not null,
   homeTeam int not null default 0,
   createdDate timestamp not null default current_timestamp(),
   modifiedDate timestamp null,
   PRIMARY KEY (gameID,teamID)
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
  sportID int  not null default 0,
  createdDate timestamp not null default current_timestamp(),
  modifiedDate timestamp null,
  PRIMARY KEY(id),
  UNIQUE INDEX(teamName)
)
ENGINE=INNODB
//

drop table if exists go_sports_lu
//

/* sports - ie football, baseball , etc */
create table go_sports_lu (
  id int not null AUTO_INCREMENT,
  leagueName varchar(50) not null,
  sportName varchar(50) not null,
  createdDate timestamp not null default current_timestamp(),
  modifiedDate timestamp null,
  PRIMARY KEY(id),
  unique index(leagueName)
)
ENGINE=INNODB
//
insert into go_sports_lu (id, leagueName, sportName) values (0,"undefined","undefined")
//
insert into go_sports_lu (leagueName,sportName) values ("NFL","Football")
//
insert into go_sports_lu (leagueName,sportName) values ("MLB","Baseball")
//
insert into go_sports_lu (leagueName,sportName) values ("NBA","Basketball")
//
insert into go_sports_lu (leagueName,sportName) values ("NHL","Hockey")
//

insert into go_teams_lu (sportID, teamName) values (3,"New York Mets")
//
insert into go_teams_lu (sportID, teamName) values (3,"New York Yankees")
//
insert into go_teams_lu (sportID, teamName) values (2,"New York Giants")
//
insert into go_teams_lu (sportID, teamName) values (2,"New York Jets")
//
insert into go_teams_lu (sportID, teamName) values (4,"New York Knicks")
//
insert into go_teams_lu (sportID, teamName) values (4,"New Jersey Nets")
//
insert into go_teams_lu (sportID, teamName) values (5,"New Jersey Devils")
//
insert into go_teams_lu (sportID, teamName) values (5,"New York Rangers")
//

/* types of "games" ie team, tournamont, etc */
drop table if exists go_types_lu
//
create table go_types_lu (
  id int not null AUTO_INCREMENT,
  typeName varchar(50) not null,
  createdDate timestamp not null default current_timestamp(),
  modifiedDate timestamp null,
  PRIMARY KEY(id),
  UNIQUE INDEX(typeName)
)
ENGINE=INNODB
//
insert into go_types_lu (id,typeName) values (0,"Undefined")
//
insert into go_types_lu (typeName) values ("Team")
//
insert into go_types_lu (typeName) values ("Tournament")
//


insert into go_publicgames (title,date,type,sport) 
values ("New York Yankees vs New York Mets 1","2010-04-06",2,3)
//
insert into go_publicgames (title,date,type,sport) 
values ("New York Yankees vs New York Mets 2","2010-04-07",2,3)
//
insert into go_publicgames (title,date,type,sport) 
values ("New York Yankees vs New York Mets 3","2010-04-08",2,3)
//
insert into go_publicgames_combatants (gameID,teamID) values (1,2)
//
insert into go_publicgames_combatants (gameID,teamID) values (1,1)
//
insert into go_publicgames_combatants (gameID,teamID) values (2,2)
//
insert into go_publicgames_combatants (gameID,teamID) values (2,1)
//
insert into go_publicgames_combatants (gameID,teamID) values (3,2)
//
insert into go_publicgames_combatants (gameID,teamID) values (3,1)
//
DELIMITER ;
