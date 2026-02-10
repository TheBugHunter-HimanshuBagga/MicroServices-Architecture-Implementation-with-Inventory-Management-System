INSERT INTO orders (order_status, total_price) VALUES
('PENDING', 1200.00),
('CONFIRMED', 2500.00),
('CANCELLED', 1800.00);


INSERT INTO order_item (product_id, quantity, order_id) VALUES
(101, 2, 1),
(102, 1, 1),
(103, 5, 2),
(104, 2, 2),
(105, 3, 3);
