CREATE TABLE users
(
  id                      BIGSERIAL NOT NULL,
  username                varchar (50) NOT NULL,
  role                    varchar (25) NOT NULL ,

  PRIMARY KEY (id)
);

CREATE TABLE credentials
(
  id                      BIGSERIAL NOT NULL,
  password                varchar (255) NOT NULL,
  user_id                 BIGINT NOT NULL ,

  PRIMARY KEY (id),
  CONSTRAINT user_id_FK FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE  user_token
(
  id                      BIGSERIAL NOT NULL,
  token_type              varchar (50) NOT NULL,
  token                   VARCHAR(512) NOT NULL UNIQUE,
  expiration_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  user_id                 BIGINT NOT NULL ,

  PRIMARY KEY (id),
  CONSTRAINT user_id_FK FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

create table device
(
  id                      bigserial not null,
  device_id               varchar(100) UNIQUE NOT NULL,
  device_status           varchar (50) NOT NULL,

  primary key (id)
);

create table unit
(
  id                      bigserial    not null,
  unit_name               varchar(100) not null,
  device_id               bigint not null,

  PRIMARY KEY (id),
  constraint device_id_FK FOREIGN KEY (device_id) REFERENCES device(id) ON DELETE CASCADE
);

create table unit_state
(
  id           bigserial not null,
  action_value varchar(25),
  state_value  varchar(25),
  time         timestamp,
  unit_id      bigint    not null,

  PRIMARY KEY (id),
  CONSTRAINT unit_id_FK FOREIGN KEY (unit_id) REFERENCES unit(id) ON DELETE CASCADE
);