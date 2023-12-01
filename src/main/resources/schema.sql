CREATE TABLE USERTABLE
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    isAdmin  VARCHAR(255)
);

CREATE TABLE FLIGHTTABLE
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    src            VARCHAR(3),
    dst            VARCHAR(3),
    code           VARCHAR(255),
    departureTime  DATETIME,
    arrivalTime    DATETIME,
    capacity       INT,
    availableSeats INT,
    user_id        INT,
    FOREIGN KEY (user_id) REFERENCES USERTABLE(id)
);