CREATE TABLE user(
                     id INT PRIMARY KEY AUTO_INCREMENT,
                     username varchar(255) UNIQUE,
                     password varchar(255),
                     isAdmin boolean
);

CREATE TABLE flight(
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       src varchar(3),
                       dst varchar(3),
                       code varchar(255),
                       departureTime datetime,
                       arrivalTime datetime,
                       capacity int,
                       availableSeats int
);
