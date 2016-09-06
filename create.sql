CREATE TABLE VOUCHER (
	id BIGINT PRIMARY KEY,
	code VARCHAR NOT NULL,
	campaign_id BIGINT,
	discount_type TINYINT,
	discount_value INT
)