CREATE TABLE "user" (
  id serial NOT NULL,
  login varchar(80) NOT NULL,
  password varchar(4096) NOT NULL,
  email varchar(1024) NOT NULL,
  active boolean NOT NULL,
  CONSTRAINT id_pkey PRIMARY KEY (id)
);
