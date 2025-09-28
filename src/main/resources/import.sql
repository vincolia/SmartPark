--Insert parking lots
INSERT INTO PARKING_LOT (lot_id, location, capacity, occupied_spaces, cost, currency) VALUES ('lot-A1', 'Ayala Street', 10, 4, 1, 'PHP');
INSERT INTO PARKING_LOT (lot_id, location, capacity, occupied_spaces, cost, currency) VALUES ('lot-B1', 'B Avenue', 5, 0, 0.5, 'PHP');

--Insert vehicles
INSERT INTO VEHICLE (license_plate, type, owner_name, parking_lot_id, check_in) VALUES ('ABC-0001', 'Car', 'John Doe', NULL, NULL);
INSERT INTO VEHICLE (license_plate, type, owner_name, parking_lot_id, check_in) VALUES ('EFG-0002', 'Car', 'Jane Su', NULL, NULL);
INSERT INTO VEHICLE (license_plate, type, owner_name, parking_lot_id, check_in) VALUES ('EFG-0003', 'Car', 'Sui Su', 'lot-A1', CURRENT_TIMESTAMP - INTERVAL '10' MINUTE);
INSERT INTO VEHICLE (license_plate, type, owner_name, parking_lot_id, check_in) VALUES ('EFG-0004', 'Car', 'Unc Rog', 'lot-A1', CURRENT_TIMESTAMP - INTERVAL '5' MINUTE);
INSERT INTO VEHICLE (license_plate, type, owner_name, parking_lot_id, check_in) VALUES ('EFG-0005', 'Truck', 'Truck kun', 'lot-A1', CURRENT_TIMESTAMP - INTERVAL '2' MINUTE);
INSERT INTO VEHICLE (license_plate, type, owner_name, parking_lot_id, check_in) VALUES ('EFG-0006', 'Motorcycle', 'Ghost Rider', 'lot-A1', CURRENT_TIMESTAMP - INTERVAL '15' MINUTE);