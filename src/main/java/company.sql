DROP DATABASE IF EXISTS candidates;

CREATE DATABASE candidates DEFAULT CHARACTER SET 'utf8';

USE candidates;

create table jobs
(
  job_id int unsigned not null auto_increment,
  jobName varchar(255) not null,
  nameCompany varchar(255) not null,
  salary varchar(255) not null,
  primary key (job_id)
) engine=InnoDB;

create table candidates
(
  candidate_id int unsigned not null auto_increment,
  firstName varchar(255) not null,
  surName varchar(255) not null,
  patronymic varchar(255) not null,
  dateOfBirth date not null,
  sex char(1),
  job_id int not null,
  phonenumber int not null,
  primary key (candidate_id)
) engine=InnoDB;

create table interviews
(
  interview_Id int unsigned not null auto_increment,
  timeInterview date not null,
  job_Id int null,
  candidate_Id int null,
  result boolean null,
  primary key (interview_Id)
)engine=InnoDB;

set names 'utf8';
