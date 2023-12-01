-- Insert data into USERTABLE
INSERT INTO USERTABLE (username, password, isAdmin) VALUES
('user1@csumb.edu', '$2a$10$SVtqhyL3dPbMYUNbCj/2.eT4DPSiqciWByHGepK/p9hzfy/dCoTB.', 'FALSE'), /*password1*/
('user2@csumb.edu', '$2a$10$F3LdzxwexyeMFrfjBLdmTesVtjzjibz7sISrT0WU.lahFKiocEtb.', 'FALSE'),/*password2*/
('admin@csumb.edu', '$2a$10$xuFNQPm2a/hB2G1qGDRMgu2NVkUxwMVZ0TnMq4ihwykoD94fcsZO2', 'TRUE');/*adminPassword*/

-- Insert data into FLIGHTTABLE
INSERT INTO FLIGHTTABLE (src, dst, code, departureTime, arrivalTime, capacity, availableSeats, user_id) VALUES
('S1', 'D1', 'FL1', '2023-01-01 08:00:00', '2023-01-01 10:00:00', 200, 200, 1),
('S2', 'D2', 'FL2', '2023-01-02 12:00:00', '2023-01-02 14:00:00', 150, 150, 2),
('S3', 'D3', 'FL3', '2023-01-03 16:00:00', '2023-01-03 18:00:00', 180, 180, 3);
