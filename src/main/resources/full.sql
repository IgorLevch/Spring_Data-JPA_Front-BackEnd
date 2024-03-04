DROP TABLE IF EXISTS products2 CASCADE;
CREATE TABLE products2 (id bigserial PRIMARY KEY, title VARCHAR(255), cost bigserial, level INTEGER, secret_key VARCHAR(255));
INSERT INTO products2 (title, cost, level, secretKey) VALUES
('milk', 80, 3, 'dffg7765ds'),
('bread', 100, 4, 'dffg7765ds'),
('coffee', 55, 5, 'dffg7765ds'),
('water', 99, 2, 'dffg7765ds');