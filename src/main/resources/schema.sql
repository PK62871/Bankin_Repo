CREATE TABLE IF NOT EXISTS customer (
  customer_id SERIAL PRIMARY KEY, -- Use SERIAL for auto-increment in PostgreSQL
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  mobile_number VARCHAR(30) NOT NULL, -- Increased length for international numbers
  created_at TIMESTAMP NOT NULL, -- Use TIMESTAMP to include time
  created_by VARCHAR(50) NOT NULL, -- Increased length for username or system identifier
  updated_at TIMESTAMP DEFAULT NULL,
  updated_by VARCHAR(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
  customer_id INT NOT NULL, -- Use same data type as the referenced column
  account_number SERIAL PRIMARY KEY, -- Use SERIAL for auto-increment
  account_type VARCHAR(100) NOT NULL,
  branch_address VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  updated_at TIMESTAMP DEFAULT NULL,
  updated_by VARCHAR(50) DEFAULT NULL,
  FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE -- Ensures referential integrity
);


